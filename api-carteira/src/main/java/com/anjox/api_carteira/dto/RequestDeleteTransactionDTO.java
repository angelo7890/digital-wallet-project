package com.anjox.api_carteira.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequestDeleteTransactionDTO(
       @NotNull UUID id
) {
}
