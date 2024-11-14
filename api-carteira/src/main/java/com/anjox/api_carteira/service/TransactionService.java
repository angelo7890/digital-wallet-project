package com.anjox.api_carteira.service;

import com.anjox.api_carteira.Exeption.ErrorExeption;
import com.anjox.api_carteira.dto.RequestTransactionDTO;
import com.anjox.api_carteira.entity.TransactionsEntity;
import com.anjox.api_carteira.entity.UserEntity;
import com.anjox.api_carteira.entity.WalletEntity;
import com.anjox.api_carteira.enums.TransactionTypeEnum;
import com.anjox.api_carteira.repository.TransactionsRepository;
import com.anjox.api_carteira.repository.UserRepository;
import com.anjox.api_carteira.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionsRepository transactionsRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public TransactionService(TransactionsRepository transactionsRepository, UserRepository userRepository, WalletRepository walletRepository) {
        this.transactionsRepository = transactionsRepository;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public TransactionsEntity createTransaction(RequestTransactionDTO dto, String username) {
        UserEntity user = validateUser(dto.userid(), username);
        WalletEntity wallet = validateWallet(user);
        validateTransactionValue(dto.value());
        TransactionsEntity transaction = new TransactionsEntity();
        transaction.setMenssagem(dto.Message());
        transaction.setValorTransacao(dto.value());
        transaction.setDateTransacao(ZonedDateTime.now(ZoneId.of("-03:00")).toLocalDateTime());
        transaction.setWallet(wallet);

        if (dto.type() == TransactionTypeEnum.SUBTRACT) {
            if (wallet.getBalance().compareTo(dto.value()) < 0) {
                throw new ErrorExeption("valor insuficiente", HttpStatus.BAD_REQUEST);
            }
            wallet.setBalance(wallet.getBalance().subtract(dto.value()));
        } else if (dto.type() == TransactionTypeEnum.DEPOSIT) {
            wallet.setBalance(wallet.getBalance().add(dto.value()));
        } else {
            throw new ErrorExeption("tipo invalido", HttpStatus.BAD_REQUEST);
        }

        transaction.setTypeTransacao(dto.type());
        walletRepository.save(wallet);
        return transactionsRepository.save(transaction);
    }

    @Transactional
    public void deleteTransaction(UUID idTransaction, String username) {
        TransactionsEntity transaction = transactionsRepository.findById(idTransaction)
                .orElseThrow(() -> new ErrorExeption("Transação não encontrada", HttpStatus.NOT_FOUND));

        UserEntity user = validateUser(transaction.getWallet().getWalletuser(), username);
        WalletEntity wallet = validateWallet(user);

        adjustBalanceForDeletedTransaction(wallet, transaction);

        transactionsRepository.delete(transaction);
        walletRepository.save(wallet);
    }

    private UserEntity validateUser(Long userId, String username) {
        UserEntity user = userRepository.findByid(userId);
        if (user == null || !user.getUsername().equals(username)) {
            throw new ErrorExeption("Usuário não encontrado ou não autorizado", HttpStatus.FORBIDDEN);
        }
        return user;
    }

    private WalletEntity validateWallet(UserEntity user) {
        WalletEntity wallet = user.getWallet();
        if (wallet == null) {
            throw new ErrorExeption("Carteira não encontrada", HttpStatus.NOT_FOUND);
        }
        return wallet;
    }

    private void validateTransactionValue(BigDecimal value) {
        if (value.compareTo(new BigDecimal("0.01")) < 0) {
            throw new ErrorExeption("O valor da transação deve ser maior ou igual a 0.01", HttpStatus.BAD_REQUEST);
        }
    }

    private void adjustBalanceForDeletedTransaction(WalletEntity wallet, TransactionsEntity transaction) {
        if (transaction.getTypeTransacao() == TransactionTypeEnum.DEPOSIT) {
            wallet.setBalance(wallet.getBalance().subtract(transaction.getValorTransacao()));
        } else if (transaction.getTypeTransacao() == TransactionTypeEnum.SUBTRACT) {
            wallet.setBalance(wallet.getBalance().add(transaction.getValorTransacao()));
        }
    }

}