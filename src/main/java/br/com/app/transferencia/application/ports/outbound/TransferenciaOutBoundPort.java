package br.com.app.transferencia.application.ports.outbound;

import br.com.app.transferencia.application.core.domain.transferencia.Transfer;

public interface TransferenciaOutBoundPort {
    void transfer(Transfer transferencia);
}
