package br.com.app.transferencia.application.core.usecase;

import br.com.app.transferencia.adapters.outbound.customers.CustomerAdapter;
import br.com.app.transferencia.adapters.outbound.customers.response.CustomerResponse;
import br.com.app.transferencia.application.core.domain.cliente.Customer;
import br.com.app.transferencia.application.mapper.CoreMapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

public class ConsultarClienteUseCase {
    private CustomerAdapter clienteAdapter;
    private CoreMapper mapper;
    public ConsultarClienteUseCase(CustomerAdapter clienteAdapter) {
        this.clienteAdapter = clienteAdapter;
        this.mapper = Mappers.getMapper(CoreMapper.class);
    }

    public Optional<Customer> consultarCliente(String idCliente) {
        Optional<CustomerResponse> cliente = clienteAdapter.getCustomerById(idCliente);
        return cliente.map(clienteResponse -> mapper.toDomain(clienteResponse));
    }
}
