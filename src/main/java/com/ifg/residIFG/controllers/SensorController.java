package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.piscinas.LeituraSensor;
import com.ifg.residIFG.repository.LeituraSensorRepository;
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

    @PostMapping("/receber")
    public void receberDados(@RequestBody Map<String, Object> dados) {
        Double temp = Double.valueOf(dados.get("t").toString());

        Integer nivelRaw = Integer.valueOf(dados.get("n").toString());
        Boolean nivel = (nivelRaw == 1);

        Double ph = Double.valueOf(dados.get("p").toString());

        Integer bombaRaw = Integer.valueOf(dados.get("b").toString());
        Boolean bombaAtiva = (bombaRaw == 1);

        LeituraSensor leitura = new LeituraSensor(temp, nivel, ph, bombaAtiva);

        repository.save(leitura);
    }

    @GetMapping("/atual")
    public LeituraSensor getAtual() {
        return repository.findTopByOrderByDataHoraDesc();
    }

    @GetMapping("/historico")
    public List<LeituraSensor> getHistorico() {
        return repository.findTop20ByOrderByDataHoraDesc();
    }
}