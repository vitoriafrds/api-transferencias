package br.com.app.transferencia.adapters.outbound.accounts.response;

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
public class AccountResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("saldo")
    private BigDecimal balance;

    @JsonProperty("ativo")
    private boolean active;

    @JsonProperty("limiteDiario")
    private BigDecimal dailyTransferLimit;
}
