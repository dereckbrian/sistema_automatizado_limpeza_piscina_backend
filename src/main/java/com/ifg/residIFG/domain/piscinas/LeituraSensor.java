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
    private Boolean nivelOk;
    private Double ph;
    private Boolean bombaAtiva;

    // --- NOVO CAMPO ---
    private Double turbidez;

    private LocalDateTime dataHora = LocalDateTime.now();

    public LeituraSensor() {}

    // Construtor atualizado recebendo a turbidez
    public LeituraSensor(Double temperatura, Boolean nivelOk, Double ph, Boolean bombaAtiva, Double turbidez) {
        this.temperatura = temperatura;
        this.nivelOk = nivelOk;
        this.ph = ph;
        this.bombaAtiva = bombaAtiva;
        this.turbidez = turbidez;
    }
}