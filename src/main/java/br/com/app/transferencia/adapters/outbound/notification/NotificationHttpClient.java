package br.com.app.transferencia.adapters.outbound.notification;

import br.com.app.transferencia.adapters.inbound.rest.request.TransferRequest;
import br.com.app.transferencia.application.exceptions.ApplicationErrorCode;
import br.com.app.transferencia.application.exceptions.ApplicationGeneralError;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Slf4j
@Component
public class NotificationHttpClient {
    private final RestTemplate restTemplate;
    private final Retry retry;

    @Value("${application.notificacoes.url}")
    private String url;

    public NotificationHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .build();
        this.retry = Retry.of("notificationRetry", retryConfig);
    }

    public void sendNotification(NotificationRequest notification) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NotificationRequest> requestEntity = new HttpEntity<>(notification, headers);

        try {
            ResponseEntity<TransferRequest> response = Retry.decorateCallable(retry, () ->
                    restTemplate.exchange(
                            this.url,
                            HttpMethod.POST,
                            requestEntity,
                            TransferRequest.class
                    )).call();

            log.info("Notificação de transferência realizada com sucesso. Chamada finalizada com status code: {}", response.getStatusCode().value());
            log.info("");
        } catch (RestClientException error) {
            log.error("Ocorreu um erro ao tentar enviar a notificação");
            throw error;
        } catch (Exception e) {
            throw new ApplicationGeneralError(ApplicationErrorCode.APPLICATION_GENERAL_ERROR);
        }
    }
}

