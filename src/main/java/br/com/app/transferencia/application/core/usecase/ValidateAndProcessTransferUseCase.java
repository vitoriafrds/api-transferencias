package br.com.app.transferencia.application.core.usecase;

import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.core.validators.Validator;
import br.com.app.transferencia.application.ports.inbound.TransferInboundPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidateAndProcessTransferUseCase implements TransferInboundPort {
    private Validator<String> customerValidator;
    private Validator<Transfer> transferValidator;
    private ExecuteAndNotifyTransferUseCase transferenciaUseCase;

    public ValidateAndProcessTransferUseCase(Validator<String> customerValidator,
                                             Validator<Transfer>  transferValidator,
                                             ExecuteAndNotifyTransferUseCase transferUseCase) {
        this.customerValidator = customerValidator;
        this.transferValidator = transferValidator;
        this.transferenciaUseCase = transferUseCase;
    }

    public void execute(Transfer transfer) {
        log.info("As validações serão aplicadas...");
        this.customerValidator.validate(transfer.getCustomerId());
        this.transferValidator.validate(transfer);

        this.transferenciaUseCase.transfer(transfer);
    }
}
