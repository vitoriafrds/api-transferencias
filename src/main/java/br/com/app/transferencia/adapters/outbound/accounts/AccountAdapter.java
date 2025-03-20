package br.com.app.transferencia.adapters.outbound.accounts;

import br.com.app.transferencia.adapters.outbound.accounts.client.AccountHttpClient;
import br.com.app.transferencia.adapters.outbound.accounts.response.AccountResponse;
import br.com.app.transferencia.application.exceptions.AccountIntegrationException;
import br.com.app.transferencia.application.exceptions.ApplicationErrorCode;
import br.com.app.transferencia.application.ports.outbound.AccountOutPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class AccountAdapter implements AccountOutPort {
    private final AccountHttpClient accountClient;

    @Autowired
    public AccountAdapter(AccountHttpClient accountClient) {
        this.accountClient = accountClient;
    }

    @Override
    @CircuitBreaker(name = "accountService", fallbackMethod = "retryGetAccount")
    public Optional<AccountResponse> getAccountById(String accountId) {
        return accountClient.getAccountById(accountId);
    }

    private Optional<AccountResponse> retryGetAccount(String accountId, Throwable t) {
        log.error("Fallback acionado para a conta {}. Erro: {}", accountId, t.getMessage());
        throw new AccountIntegrationException(ApplicationErrorCode.ACCOUNT_INTEGRATION_SERVICE_ERROR);
    }
}
