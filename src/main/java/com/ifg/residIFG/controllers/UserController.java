package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.dto.UpdateUserDTO;
import com.ifg.residIFG.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository repository;

    // Busca dados pelo email (que vamos mandar pelo front)
    @GetMapping("/me")
    public ResponseEntity<User> getUser(@RequestParam String email) {
        Optional<User> user = repository.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualiza os dados
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserDTO data) {
        Optional<User> optionalUser = repository.findByEmail(data.email());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Atualiza só o que veio
            if (data.largura() != null) user.setLarguraPiscina(data.largura());
            if (data.comprimento() != null) user.setComprimentoPiscina(data.comprimento());
            if (data.profundidade() != null) user.setProfundidadePiscina(data.profundidade());
            if (data.temperaturaMinima() != null) user.setTemperaturaMinima(data.temperaturaMinima());

            repository.save(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}