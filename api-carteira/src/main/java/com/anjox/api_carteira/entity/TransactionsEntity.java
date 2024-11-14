package com.anjox.api_carteira.entity;
import com.anjox.api_carteira.enums.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "Transact")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TransactionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal valorTransacao;

    private String menssagem;

    private LocalDateTime dateTransacao;

    private TransactionTypeEnum typeTransacao;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    @JsonBackReference
    private WalletEntity wallet;


    public TransactionsEntity(){

    }

    public TransactionsEntity( UUID id, BigDecimal valorTransacao, String menssagem, LocalDateTime dateTransacao, TransactionTypeEnum typeTransacao, WalletEntity wallet) {
        this.id = id;
        this.valorTransacao = valorTransacao;
        this.menssagem = menssagem;
        this.dateTransacao = dateTransacao;
        this.typeTransacao = typeTransacao;
        this.wallet = wallet;
    }
}