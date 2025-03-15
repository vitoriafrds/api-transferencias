package br.com.app.transferencia.application.domain;

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
    private Conta origem;
    private Conta destino;
    private BigDecimal valor;
}
