package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.historico.Historico;
import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.infra.security.HistoricoService;
import com.ifg.residIFG.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private UserRepository userRepository; // Precisamos disso para achar o dono do histórico

    @PostMapping("/historicoAdd")
    public void criarHistorico(@RequestBody Map<String, String> dados) {
        String descricao = dados.get("descricao");
        String email = dados.get("email");

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            Historico historico = new Historico();
            historico.setDescricao(descricao);
            historico.setDataEvento(LocalDateTime.now());

            // AGORA VAI FUNCIONAR PORQUE DESCOMENTAMOS NA ENTIDADE
            historico.setUsuario(userOptional.get());

            historicoService.createHistorico(historico);
        }
    }

    @DeleteMapping("/historicoLimpar")
    public ResponseEntity<Void> clearHistory() {
        try {
            historicoService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/historicoRecuperar")
    public List<Historico> getHistorico() {
        return historicoService.findAll();
    }
}