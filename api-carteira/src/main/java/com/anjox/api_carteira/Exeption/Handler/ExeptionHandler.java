package com.anjox.api_carteira.Exeption.Handler;

import com.anjox.api_carteira.Exeption.Error.ErrorResponse;
import com.anjox.api_carteira.Exeption.Error.PasswordErrorResponse;
import com.anjox.api_carteira.Exeption.ErrorExeption;
import com.anjox.api_carteira.Exeption.PasswordValidateExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExeptionHandler {
    @ExceptionHandler(PasswordValidateExeption.class)
    public ResponseEntity<PasswordErrorResponse> handlePasswordValidationException(PasswordValidateExeption exeption) {
        PasswordErrorResponse errorResponse = new PasswordErrorResponse(exeption.getErrors());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorExeption.class)
    public ResponseEntity<ErrorResponse> handleErrorException(ErrorExeption exeption) {
        ErrorResponse errorResponse = new ErrorResponse(exeption.getMessage(), exeption.getStatus());
        return new ResponseEntity<>(errorResponse, exeption.getStatus());
    }
}
