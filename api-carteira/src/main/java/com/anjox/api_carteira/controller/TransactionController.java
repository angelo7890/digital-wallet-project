package com.anjox.api_carteira.controller;
import com.anjox.api_carteira.dto.RequestDeleteTransactionDTO;
import com.anjox.api_carteira.dto.RequestTransactionDTO;
import com.anjox.api_carteira.dto.ResponseTransactionDTO;
import com.anjox.api_carteira.entity.TransactionsEntity;
import com.anjox.api_carteira.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction/create")
    public ResponseEntity<ResponseTransactionDTO> transaction(@RequestBody @Valid RequestTransactionDTO dto){

        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        TransactionsEntity transaction = transactionService.createTransaction(dto , usernameFromToken);
        ResponseTransactionDTO response = new ResponseTransactionDTO(
                transaction.getId(),
                transaction.getValorTransacao(),
                transaction.getMenssagem(),
                transaction.getTypeTransacao(),
                transaction.getDateTransacao()
        );
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/transaction/delete")
        public ResponseEntity<?> deleteTransaction(@RequestBody @Valid RequestDeleteTransactionDTO dto) {
        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        transactionService.deleteTransaction(dto.id(), usernameFromToken);
        return ResponseEntity.ok().build();
    }

}
