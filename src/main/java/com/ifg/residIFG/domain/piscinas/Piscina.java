package com.ifg.residIFG.domain.piscinas;

import com.ifg.residIFG.domain.alertas.Alertas;
import com.ifg.residIFG.domain.historico.Historico;
import com.ifg.residIFG.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "piscinas")
public class Piscina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String localizacao;
    private Double volume; // em litros
    private String tipo;   // residencial, comercial etc.

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @OneToOne(mappedBy = "piscina", cascade = CascadeType.ALL)
    private Configuracao configuracao;

    @OneToMany(mappedBy = "piscina", cascade = CascadeType.ALL)
    private List<Alertas> alertas;

    public Piscina() {

    }
}
