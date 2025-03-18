package br.com.app.transferencia.application.core.domain.transferencia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferAccount {
    private String sourceId;
    private String destinationId;
}
