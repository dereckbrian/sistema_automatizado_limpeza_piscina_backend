package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.piscinas.LeituraSensor;
import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.dto.SensorDashboardDTO; // Importe o DTO
import com.ifg.residIFG.repository.LeituraSensorRepository;
import com.ifg.residIFG.repository.UserRepository; // Importe o repo de usuario
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sensor")
@CrossOrigin(origins = "*")
public class SensorController {

    @Autowired
    private LeituraSensorRepository repository;

    @Autowired
    private UserRepository userRepository; // Injeção do repositório de usuários

    @PostMapping("/receber")
    public void receberDados(@RequestBody Map<String, Object> dados) {
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
        LeituraSensor leitura = new LeituraSensor(temp, nivel, ph, bombaAtiva, turbidez);

        repository.save(leitura);
    }

    // --- MUDANÇA AQUI ---
    @GetMapping("/atual")
    public SensorDashboardDTO getAtual() {
        // 1. Busca a última leitura do sensor
        LeituraSensor leitura = repository.findTopByOrderByDataHoraDesc();

        // 2. Busca o único usuário do sistema (para pegar as dimensões)
        List<User> users = userRepository.findAll();
        Double volumeCalculado = 0.0;

        if (!users.isEmpty()) {
            User unicoUsuario = users.get(0); // Pega o primeiro (único) usuário

            // Fórmula: Comp x Larg x Profundidade (em metros) = m3
            // Multiplica por 1000 para virar Litros
            float v = unicoUsuario.getComprimentoPiscina() * unicoUsuario.getLarguraPiscina() * unicoUsuario.getProfundidadePiscina();

            volumeCalculado = (double) (v * 1000);
        }

        // 3. Retorna o Objeto combinado
        return new SensorDashboardDTO(leitura, volumeCalculado);
    }

    @GetMapping("/historico")
    public List<LeituraSensor> getHistorico() {
        return repository.findTop20ByOrderByDataHoraDesc();
    }
}