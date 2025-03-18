package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public enum ApplicationErrorCode {
    CUSTOMER_NOT_FOUD("O cliente informado não existe.", "CLI001"),
    CONTA_INATIVA("A conta informada não está ativa", "CO01"),
    SALDO_INSUFICIENTE("Saldo insuficiente para realizar a transferência", "CO02"),
    LIMITE_DIARIO_INSUFICIENTE("Limite diário da conta insuficiente para realizar a transferência", "CO03"),
    CONTA_NAO_EXISTENTE("A conta informada não existe", "CO03"),
    TRANSFER_ERROR("", "TR001");

    private String message;
    private String code;

    ApplicationErrorCode(String mensagem, String codigo) {
        this.message = mensagem;
        this.code = codigo;
    }
}
