package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class LimiteDiarioInsuficienteException extends RuntimeException {
    private ErrorCode erro;

    public LimiteDiarioInsuficienteException(ErrorCode erro) {
        super(erro.getMensagem());
        this.erro = erro;
    }
}
