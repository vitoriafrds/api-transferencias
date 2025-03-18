package br.com.app.transferencia.application.core.validators;

import br.com.app.transferencia.application.core.domain.conta.Account;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.core.usecase.GetAccountUseCase;
import br.com.app.transferencia.application.exceptions.*;

import java.util.Optional;

public class TransferValidator implements Validator<Transfer> {
    private GetAccountUseCase contaUseCase;

    public TransferValidator(GetAccountUseCase useCase) {
        this.contaUseCase = useCase;
    }

    @Override
    public void validate(Transfer transfer) {
        Optional<Account> contaOrigem = contaUseCase.consultarConta(transfer.getAccount().getSourceId());

        if (contaOrigem.isEmpty()) {
            throw new ContaNaoExistente(ErrorCode.CONTA_NAO_EXISTENTE);
        }

       if (!contaOrigem.get().isActive()) {
           throw new ContaInativaException(ErrorCode.CONTA_INATIVA);
       }

       if (contaOrigem.get().getBalance().compareTo(transfer.getAmount()) < 0) {
           throw new SaldoInsuficienteException(ErrorCode.SALDO_INSUFICIENTE);
       }

       if (contaOrigem.get().getDailyTransferLimit().compareTo(transfer.getAmount()) < 0) {
           throw new LimiteDiarioInsuficienteException(ErrorCode.LIMITE_DIARIO_INSUFICIENTE);
       }
    }
}
