package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class ClienteNotFoundException extends RuntimeException {
    private ErrorCode erro;

    public ClienteNotFoundException(ErrorCode erro) {
        super(erro.getMensagem());
        this.erro = erro;
    }
}
