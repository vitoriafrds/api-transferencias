package br.com.app.transferencia.application.ports;

import br.com.app.transferencia.adapters.outbound.clientes.response.ClienteResponse;

import java.util.Optional;

public interface CadastroClientesOutPort {
    Optional<ClienteResponse> consultarCliente(String idCliente);
}
