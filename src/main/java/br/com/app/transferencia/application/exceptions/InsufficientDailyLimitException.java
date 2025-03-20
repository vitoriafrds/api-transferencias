package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class InsufficientDailyLimitException extends RuntimeException {
    private ApplicationErrorCode erro;

    public InsufficientDailyLimitException(ApplicationErrorCode erro) {
        super(erro.getMessage());
        this.erro = erro;
    }
}
