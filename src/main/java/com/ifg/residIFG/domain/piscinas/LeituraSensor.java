package com.ifg.residIFG.domain.piscinas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class LeituraSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double temperatura;
    private Boolean nivelCheio; // 1 = true, 0 = false

    private LocalDateTime dataHora;

    // Construtor vazio (obrigatório pro JPA)
    public LeituraSensor() {}

    // Construtor para facilitar
    public LeituraSensor(Double temperatura, Boolean nivelCheio) {
        this.temperatura = temperatura;
        this.nivelCheio = nivelCheio;
        this.dataHora = LocalDateTime.now(); // Pega a hora exata que chegou
    }
}
