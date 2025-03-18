package br.com.app.transferencia.adapters.inbound.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("id_cliente")
    private String customerId;

    @JsonProperty("valor")
    private BigDecimal amount;

    @JsonProperty("conta")
    private AccountRequest account;
}
