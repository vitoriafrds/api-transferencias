package br.com.app.transferencia.application.core.validators;

public interface Validator<T> {
    void validate(T content);
}
