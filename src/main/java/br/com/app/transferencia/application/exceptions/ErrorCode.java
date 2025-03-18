package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    CLIENTE_NAO_EXISTE("O cliente informado não existe.", "CLI001"),
    CONTA_INATIVA("A conta informada não está ativa", "CO01"),
    SALDO_INSUFICIENTE("Saldo insuficiente para realizar a transferência", "CO02"),
    LIMITE_DIARIO_INSUFICIENTE("Limite diário da conta insuficiente para realizar a transferência", "CO03"),
    CONTA_NAO_EXISTENTE("A conta informada não existe", "CO03");
    private String mensagem;
    private String codigo;

    ErrorCode(String mensagem, String codigo) {
        this.mensagem = mensagem;
        this.codigo = codigo;
    }
}
