package com.anjox.api_carteira.dto;

public record LoginResponseDTO(
        String token,
        ResponseUserDTO user
) {
}
