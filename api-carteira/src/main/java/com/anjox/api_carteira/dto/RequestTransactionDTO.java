package com.anjox.api_carteira.dto;

import com.anjox.api_carteira.enums.TransactionTypeEnum;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequestTransactionDTO(
        @NotNull Long userid,

        @NotNull BigDecimal value,

        @NotNull TransactionTypeEnum type,

        String Message
) {
}
