package br.com.app.transferencia.application.ports.outbound;

import br.com.app.transferencia.adapters.outbound.accounts.response.AccountResponse;

import java.util.Optional;

public interface AccountOutPort {
    Optional<AccountResponse> getAccountById(String accountId);
}
