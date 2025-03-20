package br.com.app.transferencia.adapters.inbound.rest.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferRequest {
    @JsonProperty("id_transferencia")
    private String transferId;

    @NotNull(message = "The field is required")
    @NotEmpty(message = "The field is required")
    @JsonProperty("id_cliente")
    private String customerId;

    @NotNull(message = "A value for the transfer must be provided")
    @Positive(message = "Please provide a value greater than 0")
    @JsonProperty("valor")
    private BigDecimal amount;

    @JsonProperty("conta")
    private AccountRequest account;
}
