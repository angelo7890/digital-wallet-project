package com.anjox.api_carteira.dto;

import jakarta.validation.constraints.NotNull;

public record RequestDeleteUserDTO(
      @NotNull Long id
) {
}
