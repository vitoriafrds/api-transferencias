package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class NotificationIntegrationException extends RuntimeException {
    private ApplicationErrorCode errorCode;

    public NotificationIntegrationException(ApplicationErrorCode erro) {
        super(erro.getMessage());

        this.errorCode = erro;
    }
}
