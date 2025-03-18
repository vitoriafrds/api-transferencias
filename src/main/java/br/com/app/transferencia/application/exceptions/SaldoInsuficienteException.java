package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class SaldoInsuficienteException extends RuntimeException {
    ApplicationErrorCode erro;

    public SaldoInsuficienteException(ApplicationErrorCode erro) {
        super(erro.getMessage());
        this.erro = erro;
    }
}
