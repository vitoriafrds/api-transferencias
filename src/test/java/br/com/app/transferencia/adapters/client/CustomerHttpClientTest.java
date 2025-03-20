package br.com.app.transferencia.adapters.client;

import br.com.app.transferencia.adapters.outbound.customers.client.CustomerHttpClient;
import br.com.app.transferencia.adapters.outbound.customers.response.CustomerResponse;
import br.com.app.transferencia.application.exceptions.ApplicationGeneralError;
import io.github.resilience4j.retry.Retry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

@ExtendWith(MockitoExtension.class)
class CustomerHttpClientTest {
    private CustomerHttpClient customerHttpClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Retry retry;


    @BeforeEach
    void setUp() {
        this.customerHttpClient = new CustomerHttpClient(restTemplate);
        ReflectionTestUtils.setField(this.customerHttpClient, "url", "dummy");
    }

    @Test
    void shouldReturnCustomer_whenGetCustomerByIdIsCalled() {
        var response = new CustomerResponse("12345", "John Doe", "123456789", "PF");
        String customerId = "12345";
        ResponseEntity<CustomerResponse> responseEntity = ResponseEntity.ok(response);
        when(restTemplate.exchange(anyString(), eq(GET), eq(null), eq(CustomerResponse.class)))
                .thenReturn(responseEntity);

        Optional<CustomerResponse> result = customerHttpClient.getCustomerById(customerId);

        assertTrue(result.isPresent());
        assertEquals(response.getId(), result.get().getId());
        assertEquals(response.getName(), result.get().getName());
    }

    @Test
    void shouldReturnEmpty_whenHttpClientErrorExceptionOccurs() {
        String customerId = "12345";
        when(restTemplate.exchange(anyString(), eq(GET), eq(null), eq(CustomerResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        Optional<CustomerResponse> result = customerHttpClient.getCustomerById(customerId);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldThrowException_whenRestClientExceptionOccurs() {
        String customerId = "12345";
        when(restTemplate.exchange(anyString(), eq(GET), eq(null), eq(CustomerResponse.class)))
                .thenThrow(new RestClientException("Connection error"));

        assertThrows(RestClientException.class, () -> customerHttpClient.getCustomerById(customerId));
    }

    @Test
    void shouldThrowApplicationGeneralError_whenUnknownExceptionOccurs() {
        String customerId = "12345";
        when(restTemplate.exchange(anyString(), eq(GET), eq(null), eq(CustomerResponse.class)))
                .thenThrow(new RuntimeException("Unknown error"));

        assertThrows(ApplicationGeneralError.class, () -> customerHttpClient.getCustomerById(customerId));
    }
}