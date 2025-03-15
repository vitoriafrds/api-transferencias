package br.com.app.transferencia.application.ports;

import br.com.app.transferencia.application.domain.Transferencia;

public interface TransferenciaInPort {
    void executar(Transferencia transferencia);
}
