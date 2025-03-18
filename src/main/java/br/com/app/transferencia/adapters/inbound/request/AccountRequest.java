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
public class AccountRequest {
    @JsonProperty("id_origem")
    private String sourceId;

    @JsonProperty("id_destino")
    private String destinationId;
}
