package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.piscinas.LeituraSensor;
import com.ifg.residIFG.repository.LeituraSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sensor")
@CrossOrigin(origins = "*") // Libera pro React acessar
public class SensorController {

    @Autowired
    private LeituraSensorRepository repository;


    @PostMapping("/receber")
    public void receberDados(@RequestBody Map<String, Object> dados) {
        Double temp = Double.valueOf(dados.get("t").toString());

        // Converte o 1 ou 0 do Arduino para Boolean
        Integer nivelRaw = Integer.valueOf(dados.get("n").toString());
        Boolean nivel = (nivelRaw == 1);

        LeituraSensor leitura = new LeituraSensor(temp, nivel);
        repository.save(leitura);
    }

    // 2. O React bate aqui para pegar a última temperatura (Card)
    @GetMapping("/atual")
    public LeituraSensor getAtual() {
        return repository.findTopByOrderByDataHoraDesc();
    }

    // 3. O React bate aqui para pegar o histórico (Gráfico)
    @GetMapping("/historico")
    public List<LeituraSensor> getHistorico() {
        return repository.findTop20ByOrderByDataHoraDesc();
    }
}
