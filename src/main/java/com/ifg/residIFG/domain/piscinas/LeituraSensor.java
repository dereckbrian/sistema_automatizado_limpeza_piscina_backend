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
    private Boolean nivelOk; // ou nivel, como você chamou

    // --- NOVOS CAMPOS ---
    private Double ph;
    private Boolean bombaAtiva;

    private LocalDateTime dataHora = LocalDateTime.now();

    // Construtor Vazio (Obrigatório pro JPA)
    public LeituraSensor() {}

    // Construtor Completo (Atualizado)
    public LeituraSensor(Double temperatura, Boolean nivelOk, Double ph, Boolean bombaAtiva) {
        this.temperatura = temperatura;
        this.nivelOk = nivelOk;
        this.ph = ph;
        this.bombaAtiva = bombaAtiva;
    }
}
