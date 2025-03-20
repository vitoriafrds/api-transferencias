package br.com.app.transferencia.application.core.validators;

import br.com.app.transferencia.application.core.domain.conta.Account;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.core.usecase.GetAccountUseCase;
import br.com.app.transferencia.application.exceptions.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class TransferValidator implements Validator<Transfer> {
    private GetAccountUseCase contaUseCase;

    public TransferValidator(GetAccountUseCase useCase) {
        this.contaUseCase = useCase;
    }

    @Override
    public void validate(Transfer transfer) {
        log.info("Iniciando validações nos dados da transferência");
        var accountId = transfer.getAccount().getSourceId();

        Optional<Account> sourceAccount = contaUseCase.getAccountById(accountId);

        if (accountIsEmpty(sourceAccount)) {
            log.error("Nenhum dado encontrado para a conta: {}", accountId);
            throw new AccountNotFoundException(ApplicationErrorCode.NON_EXISTENT_ACCOUNT);
        }

        var account = sourceAccount.get();

       if (isInactiveAccount(account)) {
           log.error("A conta não esta ativa, a transferência não será concluída...");
           throw new InactiveAccountException(ApplicationErrorCode.INACTIVE_ACCOUNT);
       }

       if (isInsufficientBalance(transfer, account)) {
           log.error("A conta não tem saldo suficiente para realizar a transação..");
           throw new InsufficientBalanceException(ApplicationErrorCode.INSUFFICIENT_BALANCE);
       }

       if (isInsufficientDailyTransferLimit(transfer, account)) {
           log.error("O limite diário para transferências nessa conta foi atingido");
           throw new InsufficientDailyLimitException(ApplicationErrorCode.INSUFFICIENT_DAILY_LIMIT);
       }

       log.info("Todas as validações aplicadas com sucesso, a trasnsferência seguirá para efetivação");
    }

    private static boolean isInsufficientDailyTransferLimit(Transfer transfer, Account sourceAccount) {
        return sourceAccount.getDailyTransferLimit().compareTo(transfer.getAmount()) < 0;
    }

    private static boolean isInsufficientBalance(Transfer transfer, Account sourceAccount) {
        return sourceAccount.getBalance().compareTo(transfer.getAmount()) < 0;
    }

    private static boolean isInactiveAccount(Account sourceAccount) {
        return !sourceAccount.isActive();
    }

    private static boolean accountIsEmpty(Optional<Account> sourceAccount) {
        return sourceAccount.isEmpty();
    }

}
