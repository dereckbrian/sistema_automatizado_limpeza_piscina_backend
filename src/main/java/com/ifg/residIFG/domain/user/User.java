package com.ifg.residIFG.domain.user;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.ifg.residIFG.domain.alertas.Alertas;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column (name = "role")
    private String role;

    @Column(name = "password")
    private String password;

    @Column(name = "larguraPiscina")
    private float larguraPiscina;

    @Column(name = "comprimentoPiscina")
    private float comprimentoPiscina;

    @Column(name = "profundidadePiscina")
    private float profundidadePiscina;

    @Column(name = "temperaturaMinima")
    private Float temperaturaMinima;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Alertas> alertas;

    public User(){

    }
}
