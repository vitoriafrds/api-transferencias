package br.com.app.transferencia.adapters.outbound.contas.response;

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
public class ContaResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("saldo")
    private BigDecimal saldo;

    @JsonProperty("ativo")
    private boolean ativo;

    @JsonProperty("limiteDiario")
    private BigDecimal limiteDiario;
}
