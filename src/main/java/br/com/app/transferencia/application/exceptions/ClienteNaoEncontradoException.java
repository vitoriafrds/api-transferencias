package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class ClienteNaoEncontradoException extends RuntimeException {
    private ErrorCode erro;

    public ClienteNaoEncontradoException(ErrorCode erro) {
        super(erro.getMensagem());
        this.erro = erro;
    }
}
