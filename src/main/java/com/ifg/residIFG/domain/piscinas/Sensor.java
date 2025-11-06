package com.ifg.residIFG.domain.piscinas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "sensores")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // pH, temperatura, turbidez, nível etc.
    private String macAddress;
    private LocalDateTime ultimaLeitura;

    @ManyToOne
    @JoinColumn(name = "piscina_id")
    private Piscina piscina;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<Parametros> parametros;

    public Sensor() {

    }
}
