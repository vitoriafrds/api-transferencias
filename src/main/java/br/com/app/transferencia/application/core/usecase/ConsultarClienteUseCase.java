package br.com.app.transferencia.application.core.usecase;

import br.com.app.transferencia.adapters.outbound.clientes.CadastroClienteAdapter;
import br.com.app.transferencia.application.core.domain.Cliente;

import java.util.Optional;

public class ConsultarClienteUseCase {
    private CadastroClienteAdapter clienteAdapter;

    public ConsultarClienteUseCase(CadastroClienteAdapter clienteAdapter) {
        this.clienteAdapter = clienteAdapter;
    }

    public Optional<Cliente> consultarCliente(String idCliente) {
        return Optional.empty();
    }
}
