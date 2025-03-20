package br.com.app.transferencia.adapters.inbound.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    @NotNull(message = "É necessário informar a conta de origem")
    @NotEmpty(message = "É necessário informar a conta de origem")
    @JsonProperty("id_origem")
    private String sourceId;

    @NotNull(message = "É necessário informar a conta destino")
    @NotEmpty(message = "É necessário informar a conta destino")
    @JsonProperty("id_destino")
    private String destinationId;
}
