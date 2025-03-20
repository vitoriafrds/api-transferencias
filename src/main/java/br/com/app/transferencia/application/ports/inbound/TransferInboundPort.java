package br.com.app.transferencia.application.ports.inbound;

import br.com.app.transferencia.application.core.domain.transferencia.Transfer;

public interface TransferInboundPort {
    void execute(Transfer transfer);
}
