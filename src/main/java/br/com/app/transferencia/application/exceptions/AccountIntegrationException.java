package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class AccountIntegrationException extends RuntimeException {
    private ApplicationErrorCode errorCode;

    public AccountIntegrationException(ApplicationErrorCode erro) {
        super(erro.getMessage());

        this.errorCode = erro;
    }
}
