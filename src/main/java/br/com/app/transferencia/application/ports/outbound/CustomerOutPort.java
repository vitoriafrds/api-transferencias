package br.com.app.transferencia.application.ports.outbound;

import br.com.app.transferencia.adapters.outbound.customers.response.CustomerResponse;

import java.util.Optional;

public interface CustomerOutPort {
    Optional<CustomerResponse> getCustomerById(String customerId);
}
