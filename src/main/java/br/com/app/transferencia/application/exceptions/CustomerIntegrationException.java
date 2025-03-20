package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class CustomerIntegrationException extends RuntimeException {
    private ApplicationErrorCode code;

    public CustomerIntegrationException(ApplicationErrorCode code) {
        super(code.getMessage());

        this.code = code;
    }
}
