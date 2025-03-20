package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class CustomerNotFoundException extends RuntimeException {
    private ApplicationErrorCode erro;

    public CustomerNotFoundException(ApplicationErrorCode erro) {
        super(erro.getMessage());
        this.erro = erro;
    }
}
