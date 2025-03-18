package br.com.app.transferencia.adapters.outbound.notification;

import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.exceptions.NotificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.time.Duration;

@Slf4j
@Component
public class NotificationProducerAdapter {
    @Value("${aws.sqs.queue.url}")
    private String bacenQueueUrl;

    private ObjectMapper mapper;
    private SqsClient client;

    private Retry retry;

    @Autowired
    public NotificationProducerAdapter(ObjectMapper mapper, SqsClient client) {
        this.mapper = mapper;
        this.client = client;

        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .build();

        this.retry = Retry.of("notificationRetry", retryConfig);
    }

    public void sendNotification(Transfer transfer) {
        log.info("Preparando evento para notificação");
        NotificationEvent event = new NotificationEvent();
        event.setAccount(transfer.getAccount());
        event.setAmount(transfer.getAmount());

        try {
            String notification = this.mapper.writeValueAsString(event);

            SendMessageRequest message = SendMessageRequest.builder()
                    .queueUrl(this.bacenQueueUrl)
                    .messageBody(notification).build();

           sendMessageOrRetry(message);

            log.info("Notificação enviada com sucesso");
        } catch (SqsException | JsonProcessingException e) {
            log.error("Erro na tentativa de envio da notificação");
            log.error("Detalhe: {}", e.getMessage());
            throw new NotificationException();
        }
    }

    private void sendMessageOrRetry(SendMessageRequest message) {
        try {
            Retry.decorateCheckedRunnable(retry, () -> {
                this.client.sendMessage(message);
            }).run();
        } catch (Throwable e) {
            log.error("Erro ao tentar enviar a notificar após {} tentativas: {}", retry.getRetryConfig().getMaxAttempts(), e.getMessage());
            throw new NotificationException();
        }
    }
}
