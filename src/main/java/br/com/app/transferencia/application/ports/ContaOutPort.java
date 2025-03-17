package br.com.app.transferencia.application.ports;

import br.com.app.transferencia.adapters.outbound.contas.response.ContaResponse;

import java.util.Optional;

public interface ContaOutPort {
    Optional<ContaResponse> consultarConta(String idConta);
}
