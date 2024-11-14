package com.anjox.api_carteira.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RequestAuthenticationDTO(
       @NotNull @NotEmpty String username,
       @NotNull @NotEmpty String password
) {
}
