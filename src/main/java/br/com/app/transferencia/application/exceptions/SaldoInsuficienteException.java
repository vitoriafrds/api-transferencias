package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class SaldoInsuficienteException extends RuntimeException {
    ErrorCode erro;

    public SaldoInsuficienteException(ErrorCode erro) {
        super(erro.getMensagem());
        this.erro = erro;
    }
}
