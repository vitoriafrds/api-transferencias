package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class TransferException extends RuntimeException {
    private ApplicationErrorCode errorCode;

    public TransferException(ApplicationErrorCode error) {
        super(error.getMessage());
    }
}
