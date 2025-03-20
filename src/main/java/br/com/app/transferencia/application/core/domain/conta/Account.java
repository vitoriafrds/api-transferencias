package br.com.app.transferencia.application.core.domain.conta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String id;
    private BigDecimal balance;
    private boolean active;
    private BigDecimal dailyTransferLimit;
}
