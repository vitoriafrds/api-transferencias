package br.com.app.transferencia.adapters.outbound.accounts.client;

import br.com.app.transferencia.adapters.outbound.accounts.response.AccountResponse;
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
public class AccountHttpClient {
    private final RestTemplate restTemplate;
    private final Retry retry;

    @Value("${application.contas.url}")
    private String url;

    public AccountHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .build();
        this.retry = Retry.of("accountRetry", retryConfig);
    }

    public Optional<AccountResponse> getAccountById(String accountId) {
        var accountUrl = formatUrl(accountId);

        try {
            ResponseEntity<AccountResponse> account = Retry.decorateCallable(retry, () ->
                    this.restTemplate.exchange(accountUrl, GET, null, AccountResponse.class)
            ).call();
            return Optional.of(account.getBody());
        } catch (HttpClientErrorException error) {
            log.error("Ocorreu um erro ao consultar a conta: {}", accountId);
        } catch (RestClientException error) {
            log.info("Falha na integração com o serviço de contas {}: {}", accountId, error.getMessage());
            throw error;
        } catch (Exception e) {
            throw new ApplicationGeneralError(ApplicationErrorCode.APPLICATION_GENERAL_ERROR);
        }

        return Optional.empty();
    }

    private String formatUrl(String accountId) {
        var placeHolderUrl = this.url.concat("{id_conta}");
        return placeHolderUrl.replace("{id_conta}", accountId);
    }
}
