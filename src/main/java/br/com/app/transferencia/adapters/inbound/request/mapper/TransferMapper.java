package br.com.app.transferencia.adapters.inbound.request.mapper;

import br.com.app.transferencia.adapters.inbound.request.AccountRequest;
import br.com.app.transferencia.adapters.inbound.request.TransferRequest;
import br.com.app.transferencia.application.core.domain.transferencia.TransferAccount;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    TransferMapper INSTANCE = Mappers.getMapper(TransferMapper.class);
    Transfer mapToDomain(TransferRequest transferencia);
    TransferAccount mapToDomain(AccountRequest conta);

    TransferRequest mapToRequest(Transfer transferencia);
    AccountRequest mapToRequest(TransferAccount conta);
}
