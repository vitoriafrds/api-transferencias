package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class LimiteDiarioInsuficienteException extends RuntimeException {
    private ApplicationErrorCode erro;

    public LimiteDiarioInsuficienteException(ApplicationErrorCode erro) {
        super(erro.getMessage());
        this.erro = erro;
    }
}
