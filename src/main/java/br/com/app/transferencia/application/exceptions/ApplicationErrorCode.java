package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public enum ApplicationErrorCode {
    CUSTOMER_NOT_FOUND("The specified customer does not exist.", "CLI001"),
    INACTIVE_ACCOUNT("The specified account is not active.", "CO01"),
    INSUFFICIENT_BALANCE("Insufficient balance to complete the transfer.", "CO02"),
    INSUFFICIENT_DAILY_LIMIT("Insufficient daily account limit to complete the transfer.", "CO03"),
    NON_EXISTENT_ACCOUNT("The specified account does not exist.", "CO03"),
    TRANSFER_ERROR("An erro occur during the transfer", "TR001"),
    ACCOUNT_INTEGRATION_SERVICE_ERROR("Error integrating with the accounts service.", "I001"),
    CUSTOMER_INTEGRATION_SERVICE_ERROR("Error integrating with the customer service.", "I002"),
    TRANSFER_INTEGRATION_SERVICE_ERROR("Error integrating with the transfer service.", "I003"),
    NOTIFICATION_INTEGRATION_SERVICE_ERROR("Error integrating with the notification service.", "I004"),
    APPLICATION_GENERAL_ERROR("Unexpected failure in the service.", "A001");

    private String message;
    private String code;

    ApplicationErrorCode(String mensagem, String codigo) {
        this.message = mensagem;
        this.code = codigo;
    }
}
