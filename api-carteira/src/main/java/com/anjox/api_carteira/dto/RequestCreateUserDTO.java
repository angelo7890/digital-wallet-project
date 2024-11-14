package com.anjox.api_carteira.dto;

import com.anjox.api_carteira.enums.UserEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RequestCreateUserDTO(
      @NotNull @NotEmpty String username,
      @NotNull @NotEmpty  String email,
      @NotNull @NotEmpty  String password,
      @NotNull  UserEnum type
) {
}
