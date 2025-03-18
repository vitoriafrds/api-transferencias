package br.com.app.transferencia.adapters.outbound.notification;


import br.com.app.transferencia.application.core.domain.transferencia.TransferAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private BigDecimal amount;
    private TransferAccount account;
}
