package br.com.app.transferencia.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transferencia {
    private String idCliente;
    private BigDecimal valor;
    private Conta conta;
}
