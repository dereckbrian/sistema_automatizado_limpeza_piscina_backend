package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.historico.Historico;
import com.ifg.residIFG.infra.security.HistoricoService;
import com.ifg.residIFG.repository.HistoricoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HistoricoController {

    private final HistoricoService historicoService;

    public HistoricoController(HistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    @PostMapping("/historicoAdd")
    public Historico criarHistorico(@RequestBody Historico historico) {
        historico.setDataEvento(LocalDateTime.now());
        return historicoService.createHistorico(historico);
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

    // Método para recuperar todos os históricos
    @GetMapping("/historicoRecuperar")
    public List<Historico> getHistorico() {
        return historicoService.findAll();  // Chama o serviço para listar os históricos
    }
}
