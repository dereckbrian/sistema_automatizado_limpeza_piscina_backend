package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.alertas.Alertas;
import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.repository.AlertasRepository;
import com.ifg.residIFG.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/alertas")
@CrossOrigin(origins = "*")
public class AlertasController {

    @Autowired
    private AlertasRepository alertasRepository;
    @Autowired
    private UserRepository userRepository;

    // Salvar um novo alerta (Chamado pelo Dashboard quando detecta problema)
    @PostMapping("/criar")
    public void criarAlerta(@RequestBody Map<String, String> dados) {
        String email = dados.get("email");
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            Alertas alerta = new Alertas(
                    dados.get("titulo"),
                    dados.get("mensagem"),
                    dados.get("tipo"),
                    user.get()
            );
            alertasRepository.save(alerta);
        }
    }

    // Listar alertas (Chamado pela tela Alerts.tsx)
    @GetMapping("/listar")
    public List<Alertas> listarAlertas(@RequestParam String email) {
        return alertasRepository.findTop20ByUsuarioEmailOrderByDataHoraDesc(email);
    }

    @DeleteMapping("/limpar")
    public ResponseEntity<Void> limparAlertas() {
        try {
            alertasRepository.deleteAll(); // Apaga tudo da tabela alertas
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}