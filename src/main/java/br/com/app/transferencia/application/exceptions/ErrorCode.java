package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    CLIENTE_NAO_EXISTE("O cliente informado não existe.", "C001");

    private String mensagem;
    private String codigo;

    ErrorCode(String mensagem, String codigo) {
        this.mensagem = mensagem;
        this.codigo = codigo;
    }
}
