package com.ifg.residIFG.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/cliente/dashboard")
    public String dashboardCliente() {
        return "Painel do Cliente: aqui você vê o status da sua piscina.";
    }

}
