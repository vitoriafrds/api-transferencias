package br.com.app.transferencia.adapters.inbound.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaRequest {
    @JsonProperty("id_cliente")
    private String idCliente;

    @JsonProperty("id_cliente")
    private String idConta;
}
