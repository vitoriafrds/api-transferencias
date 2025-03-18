package br.com.app.transferencia.application.core.validators;

import br.com.app.transferencia.application.core.domain.cliente.Customer;
import br.com.app.transferencia.application.core.usecase.GetCustomerUseCase;
import br.com.app.transferencia.application.exceptions.CustomerNotFoundException;
import br.com.app.transferencia.application.exceptions.ApplicationErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class CustomerValidator implements Validator<String> {
    private GetCustomerUseCase clienteUseCase;

    public CustomerValidator(GetCustomerUseCase clienteUseCase) {
        this.clienteUseCase = clienteUseCase;
    }

    @Override
    public void validate(String customerId) {
        log.info("Verificando se o cliente de id: {} existe na base de cadastro", customerId);
        Optional<Customer> customer = clienteUseCase.getCustomerById(customerId);

        if (customer.isEmpty()) {
            log.warn("Cadastro do cliente não encontrado");
            throw new CustomerNotFoundException(ApplicationErrorCode.CUSTOMER_NOT_FOUND);
        }

        log.info("Cliente encontrado na base, validações realizadas com sucesso");
    }
}
