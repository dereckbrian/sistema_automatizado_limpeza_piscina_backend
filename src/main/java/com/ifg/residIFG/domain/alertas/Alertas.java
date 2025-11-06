package com.ifg.residIFG.domain.alertas;

import com.ifg.residIFG.domain.piscinas.Piscina;
import com.ifg.residIFG.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
@Getter
@Setter
@AllArgsConstructor
public class Alertas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String mensagem;
    private String nivel;
    private boolean resolvido;
    private LocalDateTime dataAlerta;

    @ManyToOne
    @JoinColumn(name = "piscina_id")
    private Piscina piscina;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    public Alertas() {

    }
}
