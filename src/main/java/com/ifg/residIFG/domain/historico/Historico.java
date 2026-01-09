package com.ifg.residIFG.domain.historico;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifg.residIFG.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // Lombok cria o construtor vazio
@Table(name = "historico")
public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private LocalDateTime dataEvento;

    // --- DESCOMENTE ISSO E ADICIONE O JsonIgnore ---
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // Importante para não dar loop infinito no JSON
    private User usuario;
}