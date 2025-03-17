package br.com.app.transferencia.application.config;

import br.com.app.transferencia.adapters.inbound.TransferenciaController;
import br.com.app.transferencia.adapters.outbound.clientes.CadastroClienteAdapter;
import br.com.app.transferencia.adapters.outbound.contas.ContaAdapter;
import br.com.app.transferencia.application.core.usecase.ProcessarTransferenciaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {
    @Bean
    public ProcessarTransferenciaUseCase processarTransferenciaUseCase(CadastroClienteAdapter cadastroClienteAdapter, ContaAdapter contaAdapter) {
        return new ProcessarTransferenciaUseCase(cadastroClienteAdapter, contaAdapter);
    }

    @Bean
    public TransferenciaController transferenciaController(ProcessarTransferenciaUseCase port) {
        return new TransferenciaController(port);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
