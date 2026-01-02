package com.ifg.residIFG.dto;

public record UpdateUserDTO(String email,
                            Float largura,
                            Float comprimento,
                            Float profundidade,
                            Float temperaturaMinima) {
}
