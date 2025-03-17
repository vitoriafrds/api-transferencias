package br.com.app.transferencia.adapters.inbound.request;

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
    @JsonProperty("id_origem")
    private String idOrigem;

    @JsonProperty("id_destino")
    private String idDestino;
}
