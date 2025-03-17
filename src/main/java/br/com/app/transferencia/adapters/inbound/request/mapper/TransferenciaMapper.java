package br.com.app.transferencia.adapters.inbound.request.mapper;

import br.com.app.transferencia.adapters.inbound.request.ContaRequest;
import br.com.app.transferencia.adapters.inbound.request.TransferenciaRequest;
import br.com.app.transferencia.application.core.domain.Conta;
import br.com.app.transferencia.application.core.domain.Transferencia;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransferenciaMapper {
    TransferenciaMapper INSTANCE = Mappers.getMapper(TransferenciaMapper.class);
    Transferencia mapToDomain(TransferenciaRequest transferencia);
    Conta mapToDomain(ContaRequest conta);
}
