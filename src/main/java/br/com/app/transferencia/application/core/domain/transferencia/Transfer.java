package br.com.app.transferencia.application.core.domain.transferencia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    private String transferId;
    private String customerId;
    private BigDecimal amount;
    private TransferAccount account;
}
