package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.historico.Historico;
import com.ifg.residIFG.domain.piscinas.LeituraSensor;
import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.dto.SensorDashboardDTO;
import com.ifg.residIFG.infra.security.HistoricoService;
import com.ifg.residIFG.repository.LeituraSensorRepository;
import com.ifg.residIFG.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sensor")
@CrossOrigin(origins = "*")
public class SensorController {

    @Autowired
    private LeituraSensorRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoricoService historicoService; // <--- NOVO: Injeção do Serviço de Histórico

    // Variável em memória para guardar o último comando
    private static Boolean comandoBombaPendente = null;

    @PostMapping("/receber")
    public Map<String, Object> receberDados(@RequestBody Map<String, Object> dados) {
        // 1. Busca a ULTIMA leitura antes de salvar a nova (para comparar)
        LeituraSensor leituraAnterior = repository.findTopByOrderByDataHoraDesc();

        Double temp = Double.valueOf(dados.get("t").toString());
        Integer nivelRaw = Integer.valueOf(dados.get("n").toString());
        Boolean nivel = (nivelRaw == 1);
        Double ph = Double.valueOf(dados.get("p").toString());
        Integer bombaRaw = Integer.valueOf(dados.get("b").toString());
        Boolean bombaAtiva = (bombaRaw == 1);

        Double turbidez = 0.0;
        if (dados.get("tb") != null) {
            turbidez = Double.valueOf(dados.get("tb").toString());
        }

        // 2. Salva a Nova Leitura
        LeituraSensor novaLeitura = new LeituraSensor(temp, nivel, ph, bombaAtiva, turbidez);
        repository.save(novaLeitura);

        // --- NOVO: LÓGICA AUTOMÁTICA DE HISTÓRICO ---
        // Se já existia leitura anterior e o estado da bomba mudou
        if (leituraAnterior != null && !leituraAnterior.getBombaAtiva().equals(bombaAtiva)) {
            String acao = bombaAtiva ? "LIGOU" : "DESLIGOU";
            criarHistoricoAutomatico("O sistema detectou que a bomba " + acao);
        }
        // ---------------------------------------------

        // Resposta para o Hardware (Arduino/Postman)
        Map<String, Object> resposta = new HashMap<>();
        if (comandoBombaPendente != null) {
            resposta.put("ligarBomba", comandoBombaPendente);
        }
        return resposta;
    }

    // Método auxiliar para criar histórico pelo Backend
    private void criarHistoricoAutomatico(String descricao) {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            User usuarioSistema = users.get(0); // Usa o primeiro usuário como "Dono" do evento

            Historico historico = new Historico();
            historico.setDescricao(descricao);
            historico.setDataEvento(LocalDateTime.now());
            historico.setUsuario(usuarioSistema);

            historicoService.createHistorico(historico);
        }
    }

    @PostMapping("/comando-bomba")
    public void setComandoBomba(@RequestBody Map<String, Boolean> payload) {
        if (payload.containsKey("ligar")) {
            comandoBombaPendente = payload.get("ligar");
        }
    }

    @GetMapping("/atual")
    public SensorDashboardDTO getAtual() {
        LeituraSensor leitura = repository.findTopByOrderByDataHoraDesc();
        List<User> users = userRepository.findAll();
        Double volumeCalculado = 0.0;

        if (!users.isEmpty()) {
            User unicoUsuario = users.get(0);
            float v = unicoUsuario.getComprimentoPiscina() * unicoUsuario.getLarguraPiscina() * unicoUsuario.getProfundidadePiscina();
            volumeCalculado = (double) (v * 1000);
        }
        return new SensorDashboardDTO(leitura, volumeCalculado);
    }

    @GetMapping("/historico")
    public List<LeituraSensor> getHistorico() {
        List<LeituraSensor> lista = repository.findTop20ByOrderByDataHoraDesc();
        Collections.reverse(lista);
        return lista;
    }
}