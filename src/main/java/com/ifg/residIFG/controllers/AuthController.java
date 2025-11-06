package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.dto.LoginResquestDTO;
import com.ifg.residIFG.dto.RegisterRequestDTO;
import com.ifg.residIFG.dto.ResponseDTO;
import com.ifg.residIFG.repository.UserRepository;
import com.ifg.residIFG.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor //Serva para não precisar colocar o @Autowired em todos os private final
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginResquestDTO body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not Found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }
        return  ResponseEntity.badRequest().build();
    }

    @PostMapping( "/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body ){

        if (body.password() == null || body.password().isEmpty()) {
            return ResponseEntity.badRequest().body("A senha não pode ser nula ou vazia.");
        }
        Optional<User> user = this.repository.findByEmail(body.email());
        if (user.isEmpty()) {

            System.out.println(body.email() + "" + body.password()+ "" + body.name()+ ""+ body.role());

            /*String profilePictureUrl = null;
            if (profilePicture != null && !profilePicture.isEmpty()){
                try{
                    profilePictureUrl = saveImage(profilePicture);
                }catch (Exception e){
                    return ResponseEntity.status(500).body("Erro ao salvar a imagem");
                }
            }*/

            User user2 = new User();
            user2.setPassword(passwordEncoder.encode(body.password()));
            user2.setEmail(body.email());
            user2.setName(body.name());
            user2.setRole(body.role());
            //user2.setProfilePicture(profilePictureUrl);
            this.repository.save(user2);

            String token = this.tokenService.generateToken(user2);

            return ResponseEntity.ok(new ResponseDTO(user2.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    /*private String saveImage(MultipartFile imageFile) throws IOException {
        String uploadDir = "src/main/resources/static/images";

        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        Path upladoPath = Paths.get(uploadDir);
        if (!Files.exists(upladoPath)){
            Files.createDirectories(upladoPath);
        }
        Path filePath = upladoPath.resolve(fileName);

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/images" + fileName;
    }*/

}

