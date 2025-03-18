package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class AccountIntegrationException extends RuntimeException {
    private ApplicationErrorCode errorCode;
}
