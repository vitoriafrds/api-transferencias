package br.com.app.transferencia.application.mapper;

import br.com.app.transferencia.adapters.outbound.clientes.response.ClienteResponse;
import br.com.app.transferencia.application.core.domain.Cliente;
import org.mapstruct.Mapper;

@Mapper
public interface CoreMapper {
    Cliente toDomain(ClienteResponse cliente);
}
