package br.com.app.transferencia.adapters.outbound.customers;

import br.com.app.transferencia.adapters.outbound.customers.client.CustomerHttpClient;
import br.com.app.transferencia.adapters.outbound.customers.response.CustomerResponse;
import br.com.app.transferencia.application.exceptions.ApplicationErrorCode;
import br.com.app.transferencia.application.exceptions.CustomerIntegrationException;
import br.com.app.transferencia.application.ports.outbound.CustomerOutPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CustomerAdapter implements CustomerOutPort {
    private final CustomerHttpClient client;

    @Autowired
    public CustomerAdapter(CustomerHttpClient client) {
        this.client = client;
    }

    @Override
    @CircuitBreaker(name = "customerService", fallbackMethod = "fallbackCustomer")
    public Optional<CustomerResponse> getCustomerById(String customerId) {
        return client.getCustomerById(customerId);
    }

    private Optional<CustomerResponse> fallbackCustomer(String customerId, Throwable t) {
        log.error("Fallback acionado para o cliente {}. Erro: {}", customerId, t.getMessage());
        throw new CustomerIntegrationException(ApplicationErrorCode.CUSTOMER_INTEGRATION_SERVICE_ERROR);
    }
}
