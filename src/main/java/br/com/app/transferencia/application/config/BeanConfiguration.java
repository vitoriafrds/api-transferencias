package br.com.app.transferencia.application.config;

import br.com.app.transferencia.adapters.inbound.TransferController;
import br.com.app.transferencia.adapters.outbound.TransferAdapter;
import br.com.app.transferencia.adapters.outbound.customers.CustomerAdapter;
import br.com.app.transferencia.adapters.outbound.accounts.AccountAdapter;
import br.com.app.transferencia.application.core.usecase.ConsultarClienteUseCase;
import br.com.app.transferencia.application.core.usecase.GetAccountUseCase;
import br.com.app.transferencia.application.core.usecase.ValidateAndProcessTransferUseCase;
import br.com.app.transferencia.application.core.usecase.ExecuteTransferUseCase;
import br.com.app.transferencia.application.core.validators.TransferValidator;
import br.com.app.transferencia.application.core.validators.CustomerValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {
    @Bean
    public ValidateAndProcessTransferUseCase processarTransferenciaUseCase(CustomerValidator customerValidator,
                                                                           TransferValidator bankTransferValidator,
                                                                           ExecuteTransferUseCase transferenciaUseCase) {
        return new ValidateAndProcessTransferUseCase(customerValidator, bankTransferValidator, transferenciaUseCase);
    }

    @Bean
    public TransferController transferenciaController(ValidateAndProcessTransferUseCase port) {
        return new TransferController(port);
    }

    @Bean
    public ConsultarClienteUseCase consultarClienteUseCase(CustomerAdapter clienteAdapter) {
        return new ConsultarClienteUseCase(clienteAdapter);
    }

    @Bean
    public GetAccountUseCase consultarContaUseCase(AccountAdapter contaAdapter) {
        return new GetAccountUseCase(contaAdapter);
    }

    @Bean
    public ExecuteTransferUseCase realizarTransferenciaUseCase(TransferAdapter adapter) {
        return new ExecuteTransferUseCase(adapter);
    }

    @Bean
    public CustomerValidator customerValidator(ConsultarClienteUseCase clienteUseCase) {
        return new CustomerValidator(clienteUseCase);
    }

    public TransferValidator bankTransferValidator(GetAccountUseCase contaUseCase) {
        return new TransferValidator(contaUseCase);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
