package com.ifg.residIFG.domain.piscinas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "configuracoes")
public class Configuracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double limitePhMin;
    private Double limitePhMax;
    private Double limiteTurbidez;
    private Integer tempoFiltragemMin; // em minutos

    @OneToOne
    @JoinColumn(name = "piscina_id")
    private Piscina piscina;

    public Configuracao() {

    }
}
