package br.com.app.transferencia.config;

import br.com.app.transferencia.adapters.outbound.TransferAdapter;
import br.com.app.transferencia.adapters.outbound.accounts.AccountAdapter;
import br.com.app.transferencia.adapters.outbound.customers.CustomerAdapter;
import br.com.app.transferencia.adapters.outbound.notification.NotificationProducerAdapter;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.core.usecase.*;
import br.com.app.transferencia.application.core.validators.CustomerValidator;
import br.com.app.transferencia.application.core.validators.TransferValidator;
import br.com.app.transferencia.application.core.validators.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {
    @Bean
    public ValidateAndProcessTransferUseCase processarTransferenciaUseCase(Validator<String> customerValidator,
                                                                           Validator<Transfer>  transferValidator,
                                                                           ExecuteAndNotifyTransferUseCase transferenciaUseCase) {
        return new ValidateAndProcessTransferUseCase(customerValidator, transferValidator, transferenciaUseCase);
    }

    @Bean
    public GetCustomerUseCase consultarClienteUseCase(CustomerAdapter clienteAdapter) {
        return new GetCustomerUseCase(clienteAdapter);
    }

    @Bean
    public GetAccountUseCase consultarContaUseCase(AccountAdapter contaAdapter) {
        return new GetAccountUseCase(contaAdapter);
    }

    @Bean
    public ExecuteAndNotifyTransferUseCase realizarTransferenciaUseCase(TransferAdapter adapter, NotificationProducerAdapter notificationAdapter) {
        return new ExecuteAndNotifyTransferUseCase(adapter, notificationAdapter);
    }

    @Bean
    public CustomerValidator customerValidator(GetCustomerUseCase clienteUseCase) {
        return new CustomerValidator(clienteUseCase);
    }

    @Bean
    public TransferValidator bankTransferValidator(GetAccountUseCase contaUseCase) {
        return new TransferValidator(contaUseCase);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
