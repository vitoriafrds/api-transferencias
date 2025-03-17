package br.com.app.transferencia.application.core.usecase;

import br.com.app.transferencia.adapters.outbound.clientes.CadastroClienteAdapter;
import br.com.app.transferencia.adapters.outbound.contas.ContaAdapter;
import br.com.app.transferencia.application.core.domain.Transferencia;
import br.com.app.transferencia.application.ports.TransferenciaInPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessarTransferenciaUseCase implements TransferenciaInPort {
    private CadastroClienteAdapter cadastroClienteAdapter;
    private ContaAdapter contaAdapter;

    public ProcessarTransferenciaUseCase(CadastroClienteAdapter cadastroClienteAdapter, ContaAdapter contaAdapter) {
        this.cadastroClienteAdapter = cadastroClienteAdapter;
        this.contaAdapter = contaAdapter;
    }

    @Override
    public void executar(Transferencia transferencia) {
        var idCliente = transferencia.getIdCliente();
        var idConta = transferencia.getConta().getIdOrigem();
        this.cadastroClienteAdapter.consultarCliente(idCliente);
        this.contaAdapter.consultarConta(idConta);
        //TODO: Chamar adapter com integração com api de contas
        log.info("Iniciando processamento de transferencia");
    }
}
