package com.anjox.api_carteira.Exeption.Error;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private String error;
    private HttpStatus status;

    public ErrorResponse(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
