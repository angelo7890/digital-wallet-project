package com.anjox.api_carteira.dto;

import com.anjox.api_carteira.entity.WalletEntity;

public record ResponseUserDTO(
        Long id,
        String username,
        String email,
        WalletEntity wallet
) {

}
