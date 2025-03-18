package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class AccountNotFoundException extends RuntimeException {
    private ApplicationErrorCode erro;
    public AccountNotFoundException(ApplicationErrorCode erro) {
        super(erro.getMessage());

        this.erro = erro;
    }
}
