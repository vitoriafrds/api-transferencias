package br.com.app.transferencia.adapters.inbound.request;

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
    @JsonProperty("id_cliente")
    private String idCliente;

    @JsonProperty("valor")
    private BigDecimal valor;

    @JsonProperty("conta")
    private ContaRequest conta;

}
