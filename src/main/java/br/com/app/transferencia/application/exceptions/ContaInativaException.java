package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class ContaInativaException extends RuntimeException {
    private ErrorCode erro;

    public ContaInativaException(ErrorCode erro) {
        super(erro.getMensagem());
        this.erro = erro;
    }
}
