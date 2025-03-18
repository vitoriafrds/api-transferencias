package br.com.app.transferencia.adapters.outbound.customers.client;

import br.com.app.transferencia.adapters.outbound.customers.response.CustomerResponse;
import br.com.app.transferencia.application.exceptions.ApplicationErrorCode;
import br.com.app.transferencia.application.exceptions.ApplicationGeneralError;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
public class CustomerHttpClient {
    private final RestTemplate restTemplate;
    private final Retry retry;

    @Value("${application.clientes.url}")
    private String url;

    public CustomerHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .build();
        this.retry = Retry.of("customerRetry", retryConfig);
    }

    public Optional<CustomerResponse> getCustomerById(String customerId) {
        var accountUrl = formatUrl(customerId);

        try {
            ResponseEntity<CustomerResponse> account = Retry.decorateCallable(retry, () ->
                    this.restTemplate.exchange(accountUrl, GET, null, CustomerResponse.class)
            ).call();

            log.info("Consulta para o cliente: {} realizada com sucesso", customerId);
            return Optional.of(account.getBody());
        } catch (HttpClientErrorException error) {
            log.error("Ocorreu um erro ao consultar o cliente: {}", customerId);
        } catch (RestClientException error) {
            log.info("Falha na integração com o serviço de cadastro de clientes: {}", error.getMessage());
            throw error;
        } catch (Exception e) {
            throw new ApplicationGeneralError(ApplicationErrorCode.APPLICATION_GENERAL_ERROR);
        }

        return Optional.empty();
    }


    private String formatUrl(String idCliente) {
        var placeHolderUrl =  this.url.concat("{id_cliente}");
        return placeHolderUrl.replace("{id_cliente}", idCliente);
    }
}

