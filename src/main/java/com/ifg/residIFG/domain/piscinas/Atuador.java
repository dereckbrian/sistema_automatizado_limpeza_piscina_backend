package com.ifg.residIFG.domain.piscinas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "atuadores")
public class Atuador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // bomba, válvula, aquecedor
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "piscina_id")
    private Piscina piscina;

    public Atuador() {

    }
}
