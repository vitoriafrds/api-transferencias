package br.com.app.transferencia.adapters;

import br.com.app.transferencia.adapters.outbound.notification.NotificationEvent;
import br.com.app.transferencia.adapters.outbound.notification.NotificationProducerAdapter;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.core.domain.transferencia.TransferAccount;
import br.com.app.transferencia.application.exceptions.NotificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationProducerAdapterTest {
    private NotificationProducerAdapter notificationProducerAdapter;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private SqsClient sqsClient;

    @BeforeEach
    void setUp() {
        this.notificationProducerAdapter = new NotificationProducerAdapter(objectMapper, sqsClient);
        ReflectionTestUtils.setField(notificationProducerAdapter, "bacenQueueUrl", "dummy");
    }

    @Test
    void shouldSendNotificationSuccessfully_whenTransferIsValid() throws JsonProcessingException {
        var transfer = new Transfer();
        transfer.setAccount(new TransferAccount("123", "456"));
        transfer.setAmount(BigDecimal.valueOf(20));

        when(objectMapper.writeValueAsString(any(NotificationEvent.class))).thenReturn("notification");

        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl("someQueueUrl")
                .messageBody("notification").build();

        notificationProducerAdapter.sendNotification(transfer);


        verify(objectMapper, times(1)).writeValueAsString(any(NotificationEvent.class));
        verify(sqsClient, times(1)).sendMessage(sendMessageRequest);
    }

    @Test
    void shouldThrowNotificationException_whenJsonProcessingExceptionOccurs() throws JsonProcessingException {
        var transfer = new Transfer();
        transfer.setAccount(new TransferAccount("123", "456"));
        transfer.setAmount(BigDecimal.valueOf(20));

        when(objectMapper.writeValueAsString(any(NotificationEvent.class)))
                .thenThrow(new JsonProcessingException("Error processing JSON") {});

        assertThrows(NotificationException.class, () -> notificationProducerAdapter.sendNotification(transfer));
    }

    @Test
    void shouldThrowNotificationException_whenSqsExceptionOccurs() throws JsonProcessingException {
        var transfer = new Transfer();
        transfer.setAccount(new TransferAccount("123", "456"));
        transfer.setAmount(BigDecimal.valueOf(20));

        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl("dummy")
                .messageBody("notification").build();

        when(sqsClient.sendMessage(sendMessageRequest)).thenThrow(SqsException.class);
        when(objectMapper.writeValueAsString(any(NotificationEvent.class))).thenReturn("notification");

        assertThrows(NotificationException.class, () -> notificationProducerAdapter.sendNotification(transfer));
    }
}