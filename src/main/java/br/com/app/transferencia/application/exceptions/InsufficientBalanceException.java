package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class InsufficientBalanceException extends RuntimeException {
    ApplicationErrorCode erro;

    public InsufficientBalanceException(ApplicationErrorCode erro) {
        super(erro.getMessage());
        this.erro = erro;
    }
}
