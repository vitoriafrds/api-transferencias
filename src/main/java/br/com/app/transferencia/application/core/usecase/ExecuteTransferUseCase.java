package br.com.app.transferencia.application.core.usecase;

import br.com.app.transferencia.adapters.outbound.TransferAdapter;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;

public class ExecuteTransferUseCase {
    private TransferAdapter transferAdapter;

    public ExecuteTransferUseCase(TransferAdapter transferenciaAdapter) {
        this.transferAdapter = transferenciaAdapter;
    }

    public void execute(Transfer transfer) {
        this.transferAdapter.transfer(transfer);
    }
}
