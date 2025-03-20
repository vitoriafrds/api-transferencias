package br.com.app.transferencia.adapters.outbound.notification;

import br.com.app.transferencia.adapters.outbound.customers.response.CustomerResponse;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.exceptions.ApplicationErrorCode;
import br.com.app.transferencia.application.exceptions.CustomerIntegrationException;
import br.com.app.transferencia.application.exceptions.NotificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.util.Optional;

@Slf4j
@Component
public class NotificationAdapter {
    private NotificationHttpClient httpClient;

    @Autowired
    public NotificationAdapter(NotificationHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @CircuitBreaker(name = "notificationService", fallbackMethod = "fallbackNotification")
    public void sendNotification(Transfer transfer) {
        log.info("Preparando evento para notificação");

        NotificationRequest notification = new NotificationRequest();
        notification.setAccount(transfer.getAccount());
        notification.setAmount(transfer.getAmount());

        this.httpClient.sendNotification(notification);
    }

    private void fallbackNotification(Transfer transfer, Throwable t) {
        log.error("Fallback acionado para o cliente. Erro: {}", t.getMessage());
        throw new CustomerIntegrationException(ApplicationErrorCode.CUSTOMER_INTEGRATION_SERVICE_ERROR);
    }
}
