package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class ContaNaoExistente extends RuntimeException {
    private ErrorCode erro;
    public ContaNaoExistente(ErrorCode erro) {
        super(erro.getMensagem());

        this.erro = erro;
    }
}
