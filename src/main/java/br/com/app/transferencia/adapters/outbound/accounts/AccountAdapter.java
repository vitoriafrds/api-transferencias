package br.com.app.transferencia.adapters.outbound.accounts;

import br.com.app.transferencia.adapters.outbound.accounts.response.AccountResponse;
import br.com.app.transferencia.application.ports.outbound.AccountOutPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
public class AccountAdapter implements AccountOutPort {
    private RestTemplate restTemplate;

    @Value("${application.contas.url}")
    private String url;

    private Retry retry;

    public AccountAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .build();

        this.retry = Retry.of("accountRetry", retryConfig);
    }

    @Override
    @CircuitBreaker(name = "accountService", fallbackMethod = "retryGetAccount")
    public Optional<AccountResponse> getAccountById(String accountId) {
        var accountServiceUrl = formatUrl(accountId);

        try {
            ResponseEntity<AccountResponse> conta = this.restTemplate
                    .exchange(accountServiceUrl, GET, null, AccountResponse.class);

            return Optional.of(conta.getBody());
        } catch (HttpClientErrorException error) {
            log.error("Ocorreu um erro ao consultar a conta: {}", accountId);
        } catch (RestClientException error) {
            log.info("Falha na integração com o serviço de contas {}: {}", accountId, error.getMessage());
        }

        return Optional.empty();
    }

    private Optional<AccountResponse> retryGetAccount(String accountId, Throwable t) {
        log.error("Fallback acionado para a conta {}. Erro: {}", accountId, t.getMessage());

        try {
            log.info("Tentando integração novamente...");
            return Retry.decorateCallable(retry, () -> getAccountById(accountId)).call();
        } catch (Exception e) {
            log.error("Falha ao tentar novamente consultar a conta {}: {}", accountId, e.getMessage());
            return Optional.empty();
        }
    }

    private String formatUrl(String accountId) {
        //TODO: Criar uma classe de configuracao para esse dado
        var placeHolderUrl =  this.url.concat("{id_conta}");
        return placeHolderUrl.replace("{id_conta}", accountId);
    }
}
