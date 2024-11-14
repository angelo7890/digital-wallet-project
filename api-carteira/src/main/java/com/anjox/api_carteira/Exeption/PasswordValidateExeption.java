package com.anjox.api_carteira.Exeption;

import java.util.List;

public class PasswordValidateExeption extends RuntimeException{

    private final List<String> errors;

    public PasswordValidateExeption(List<String> errors) {
        super("A senha não atende aos requisitos.");
        this.errors = errors;
    }
    public List<String> getErrors() {
        return errors;
    }
}
