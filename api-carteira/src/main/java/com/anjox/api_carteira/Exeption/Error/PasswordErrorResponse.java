package com.anjox.api_carteira.Exeption.Error;

import java.util.List;

public class PasswordErrorResponse {
    private List<String> errors;

    public PasswordErrorResponse(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
