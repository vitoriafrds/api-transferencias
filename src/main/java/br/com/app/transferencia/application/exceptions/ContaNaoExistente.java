package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class ContaNaoExistente extends RuntimeException {
    private ApplicationErrorCode erro;
    public ContaNaoExistente(ApplicationErrorCode erro) {
        super(erro.getMessage());

        this.erro = erro;
    }
}
