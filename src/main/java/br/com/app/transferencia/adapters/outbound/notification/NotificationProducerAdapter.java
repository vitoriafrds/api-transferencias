package br.com.app.transferencia.adapters.outbound.notification;

import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.exceptions.NotificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

@Slf4j
@Component
public class NotificationProducerAdapter {
    @Value("${aws.sqs.queue.url}")
    private String bacenQueueUrl;

    private ObjectMapper mapper;
    private SqsClient client;


    @Autowired
    public NotificationProducerAdapter(ObjectMapper mapper, SqsClient client) {
        this.mapper = mapper;
        this.client = client;
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

            sendMessage(message);

            log.info("Notificação enviada com sucesso");
        } catch (SqsException | JsonProcessingException e) {
            log.error("Erro na tentativa de envio da notificação");
            log.error("Detalhe: {}", e.getMessage());
            throw new NotificationException();
        }
    }

    private void sendMessage(SendMessageRequest message) {
        this.client.sendMessage(message);
    }
}
