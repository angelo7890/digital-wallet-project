package com.anjox.api_carteira.repository;

import com.anjox.api_carteira.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TransactionsRepository extends JpaRepository<TransactionsEntity, UUID> {

    TransactionsEntity findByid(UUID id);

}
