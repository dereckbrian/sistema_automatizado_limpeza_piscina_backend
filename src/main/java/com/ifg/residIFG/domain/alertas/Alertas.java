package com.ifg.residIFG.domain.alertas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifg.residIFG.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
@Getter
@Setter
public class Alertas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;
    private String tipo; // "warning", "info", "success"

    private LocalDateTime dataHora;

    // --- NOVO CAMPO PARA CORRIGIR O ERRO DO BANCO ---
    @Column(nullable = false)
    private Boolean resolvido = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User usuario;

    // --- CONSTRUTORES ---
    public Alertas() {
    }

    public Alertas(String titulo, String mensagem, String tipo, User user) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.usuario = user;
        this.dataHora = LocalDateTime.now();
        this.resolvido = false; // Padrão: nasce como não resolvido
    }

}