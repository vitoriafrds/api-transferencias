package br.com.app.transferencia.adapters.outbound.notification;


import br.com.app.transferencia.application.core.domain.transferencia.TransferAccount;
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
public class NotificationRequest {
    @JsonProperty("valor")
    private BigDecimal amount;
    @JsonProperty("conta")
    private TransferAccount account;
}
