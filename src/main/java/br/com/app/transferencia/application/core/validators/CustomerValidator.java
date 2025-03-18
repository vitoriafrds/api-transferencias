package br.com.app.transferencia.application.core.validators;

import br.com.app.transferencia.application.core.domain.cliente.Customer;
import br.com.app.transferencia.application.core.usecase.ConsultarClienteUseCase;
import br.com.app.transferencia.application.exceptions.ClienteNaoEncontradoException;
import br.com.app.transferencia.application.exceptions.ErrorCode;

import java.util.Optional;

public class CustomerValidator implements Validator<String> {
    private ConsultarClienteUseCase clienteUseCase;

    public CustomerValidator(ConsultarClienteUseCase clienteUseCase) {
        this.clienteUseCase = clienteUseCase;
    }

    @Override
    public void validate(String idCliente) {
        Optional<Customer> clienteDestino = clienteUseCase.consultarCliente(idCliente);

        if (clienteDestino.isEmpty()) {
            throw new ClienteNaoEncontradoException(ErrorCode.CLIENTE_NAO_EXISTE);
        }
    }
}
