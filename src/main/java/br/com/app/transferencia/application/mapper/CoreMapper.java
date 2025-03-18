package br.com.app.transferencia.application.mapper;

import br.com.app.transferencia.adapters.outbound.customers.response.CustomerResponse;
import br.com.app.transferencia.adapters.outbound.accounts.response.AccountResponse;
import br.com.app.transferencia.application.core.domain.cliente.Customer;
import br.com.app.transferencia.application.core.domain.conta.Account;
import org.mapstruct.Mapper;

@Mapper
public interface CoreMapper {
    Customer toDomain(CustomerResponse cliente);
    Account toDomain(AccountResponse conta);
}
