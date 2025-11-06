package com.ifg.residIFG.domain.piscinas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "parametros")
public class Parametros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private Double valor;
    private LocalDateTime dataLeitura;

    @ManyToOne
    @JoinColumn(name = "piscina_id")
    private Piscina piscina;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    public Parametros() {

    }
}
