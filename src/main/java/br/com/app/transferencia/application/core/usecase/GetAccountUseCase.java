package br.com.app.transferencia.application.core.usecase;

import br.com.app.transferencia.adapters.outbound.accounts.AccountAdapter;
import br.com.app.transferencia.adapters.outbound.accounts.response.AccountResponse;
import br.com.app.transferencia.application.core.domain.conta.Account;
import br.com.app.transferencia.application.mapper.CoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Slf4j
public class GetAccountUseCase {
    private AccountAdapter contaAdapter;
    private CoreMapper mapper;
    public GetAccountUseCase(AccountAdapter adapter) {
        this.contaAdapter = adapter;
        this.mapper = Mappers.getMapper(CoreMapper.class);
    }

    public Optional<Account> consultarConta(String id) {
        Optional<AccountResponse> conta = contaAdapter.getAccountById(id);
        return conta.map(contaResponse -> mapper.toDomain(contaResponse));
    }
}
