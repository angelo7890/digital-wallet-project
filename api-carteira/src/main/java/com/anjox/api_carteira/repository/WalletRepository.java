package com.anjox.api_carteira.repository;

import com.anjox.api_carteira.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity,Long> {
}
