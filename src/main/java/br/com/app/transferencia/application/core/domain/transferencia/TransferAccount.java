package br.com.app.transferencia.application.core.domain.transferencia;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferAccount {
    @JsonProperty("idOrigem")
    private String sourceId;

    @JsonProperty("idDestino")
    private String destinationId;
}
