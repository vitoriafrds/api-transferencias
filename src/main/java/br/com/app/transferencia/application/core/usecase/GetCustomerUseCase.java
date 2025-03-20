package br.com.app.transferencia.application.core.usecase;

import br.com.app.transferencia.adapters.outbound.customers.CustomerAdapter;
import br.com.app.transferencia.adapters.outbound.customers.response.CustomerResponse;
import br.com.app.transferencia.application.core.domain.cliente.Customer;
import br.com.app.transferencia.application.mapper.CoreMapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

public class GetCustomerUseCase {
    private CustomerAdapter clienteAdapter;
    private CoreMapper mapper;
    public GetCustomerUseCase(CustomerAdapter clienteAdapter) {
        this.clienteAdapter = clienteAdapter;
        this.mapper = Mappers.getMapper(CoreMapper.class);
    }

    public Optional<Customer> getCustomerById(String idCliente) {
        Optional<CustomerResponse> customer = clienteAdapter.getCustomerById(idCliente);
        return customer.map(customerResponse -> mapper.toDomain(customerResponse));
    }
}
