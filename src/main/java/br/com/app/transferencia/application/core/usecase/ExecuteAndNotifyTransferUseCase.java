package br.com.app.transferencia.application.core.usecase;

import br.com.app.transferencia.adapters.outbound.TransferAdapter;
import br.com.app.transferencia.adapters.outbound.notification.NotificationProducerAdapter;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.exceptions.NotificationException;
import br.com.app.transferencia.application.ports.outbound.NotificationOutPort;
import br.com.app.transferencia.application.ports.outbound.TransferenciaOutBoundPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecuteAndNotifyTransferUseCase implements TransferenciaOutBoundPort, NotificationOutPort {
    private final TransferAdapter transferAdapter;
    private final NotificationProducerAdapter notificationAdapter;

    public ExecuteAndNotifyTransferUseCase(TransferAdapter transferenciaAdapter, NotificationProducerAdapter notificationAdapter) {
        this.transferAdapter = transferenciaAdapter;
        this.notificationAdapter = notificationAdapter;
    }

    @Override
    public void transfer(Transfer transfer) {
        this.transferAdapter.transfer(transfer);

        try {
            this.notify(transfer);
        } catch (NotificationException error) {
            log.warn("A tentativa de envio de notificação falhou, mas a transferência ocorreu com sucesso");
        }

    }

    @Override
    public void notify(Transfer transfer) {
        this.notificationAdapter.sendNotification(transfer);
    }
}
