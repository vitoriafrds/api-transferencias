package br.com.app.transferencia.adapters.outbound.transfer;

import br.com.app.transferencia.adapters.outbound.transfer.client.TransferHttpClient;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.exceptions.ApplicationErrorCode;
import br.com.app.transferencia.application.exceptions.TransferIntegrationException;
import br.com.app.transferencia.application.ports.outbound.TransferenciaOutBoundPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransferAdapter implements TransferenciaOutBoundPort {
    private final TransferHttpClient client;
    @Autowired
    public TransferAdapter(TransferHttpClient client) {
        this.client = client;
    }

    @Override
    @CircuitBreaker(name = "transferService", fallbackMethod = "fallbackTransfer")
    public void transfer(Transfer transfer) {
       client.transfer(transfer);
    }

    private void fallbackTransfer(String customerId, Throwable t) {
        log.error("Fallback acionado para a rota de transferência. Erro: {}", t.getMessage());
        throw new TransferIntegrationException(ApplicationErrorCode.CUSTOMER_INTEGRATION_SERVICE_ERROR);
    }
}
