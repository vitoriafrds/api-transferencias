package br.com.app.transferencia.adapters.outbound.clientes.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private String id;
    private String nome;
    private String telefone;
    private String tipoPessoa;
}
