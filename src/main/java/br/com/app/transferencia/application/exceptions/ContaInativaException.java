package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class ContaInativaException extends RuntimeException {
    private ApplicationErrorCode erro;

    public ContaInativaException(ApplicationErrorCode erro) {
        super(erro.getMessage());
        this.erro = erro;
    }
}
