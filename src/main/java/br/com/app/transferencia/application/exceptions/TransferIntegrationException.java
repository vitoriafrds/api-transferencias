package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class TransferIntegrationException extends RuntimeException {
    private ApplicationErrorCode code;

    public TransferIntegrationException(ApplicationErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
