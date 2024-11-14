package com.anjox.api_carteira.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Wallet")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq")
    @SequenceGenerator(name = "wallet_seq", sequenceName = "wallet_sequence", allocationSize = 1)
    private Long id;

    private BigDecimal balance;

    private Long walletuser;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TransactionsEntity> transactions = new ArrayList<>();

    public WalletEntity(BigDecimal balance) {
        this.balance = balance;
    }

    public WalletEntity(BigDecimal balance, List<TransactionsEntity> transactions) {
        this.balance = balance;
        this.transactions = transactions;
    }
}
