package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class InactiveAccountException extends RuntimeException {
    private ApplicationErrorCode erro;

    public InactiveAccountException(ApplicationErrorCode erro) {
        super(erro.getMessage());
        this.erro = erro;
    }
}
