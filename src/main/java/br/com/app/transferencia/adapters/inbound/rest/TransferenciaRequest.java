package br.com.app.transferencia.adapters.inbound.rest;

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
public class TransferenciaRequest {
    @JsonProperty("origem")
    private ContaRequest origem;

    @JsonProperty("destino")
    private ContaRequest destino;

    @JsonProperty("valor")
    private BigDecimal valor;
}
