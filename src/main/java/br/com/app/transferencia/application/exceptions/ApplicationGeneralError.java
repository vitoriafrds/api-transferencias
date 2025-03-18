package br.com.app.transferencia.application.exceptions;

import lombok.Getter;

@Getter
public class ApplicationGeneralError extends RuntimeException {
    private ApplicationErrorCode errorCode;

    public ApplicationGeneralError(ApplicationErrorCode erro) {
        super(erro.getMessage());

        this.errorCode = erro;
    }
}
