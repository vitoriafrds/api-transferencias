package br.com.app.transferencia.adapters.outbound.transfer.client;

import br.com.app.transferencia.adapters.inbound.rest.request.TransferRequest;
import br.com.app.transferencia.adapters.inbound.rest.request.mapper.TransferMapper;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.exceptions.ApplicationErrorCode;
import br.com.app.transferencia.application.exceptions.ApplicationGeneralError;
import br.com.app.transferencia.application.exceptions.TransferException;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Slf4j
@Component
public class TransferHttpClient {
    @Value("${application.transferencias.url}")
    private String url;

    private TransferMapper mapper;
    private final RestTemplate restTemplate;
    private final Retry retry;


    public TransferHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.mapper = TransferMapper.INSTANCE;

        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .build();
        this.retry = Retry.of("transferRetry", retryConfig);
    }

    public void transfer(Transfer transfer) {
        log.info("Iniciando processo de transferência");
        HttpHeaders headers = buildRequestHeaders();
        TransferRequest transferRequest = mapper.mapToRequest(transfer);

        HttpEntity<TransferRequest> requestEntity = new HttpEntity<>(transferRequest, headers);

        try {
            ResponseEntity<TransferRequest> response = Retry.decorateCallable(retry, () ->
                    restTemplate.exchange(
                    this.url,
                    HttpMethod.PUT,
                    requestEntity,
                    TransferRequest.class
            )).call();

            log.info("Chamada finalizada com status code: {}", response.getStatusCode().value());
            log.info("Transferência realizada com sucesso");
        } catch (RestClientException error) {
            log.error("Ocorreu um erro ao realizar a transferência");
            throw error;
        } catch (Exception e) {
            throw new ApplicationGeneralError(ApplicationErrorCode.APPLICATION_GENERAL_ERROR);
        }
    }
    private HttpHeaders buildRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}
