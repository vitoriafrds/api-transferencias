package br.com.app.transferencia.adapters.client;

import br.com.app.transferencia.adapters.outbound.accounts.client.AccountHttpClient;
import br.com.app.transferencia.adapters.outbound.accounts.response.AccountResponse;
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

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

@ExtendWith(MockitoExtension.class)
class AccountHttpClientTest {

    private AccountHttpClient accountHttpClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Retry retry;

    @BeforeEach
    public void setUp() {
        this.accountHttpClient = new AccountHttpClient(restTemplate);
        ReflectionTestUtils.setField(this.accountHttpClient, "url", "dummy");
    }

    @Test
    void shouldReturnAccount_whenGetAccountByIdIsCalled() {
        AccountResponse accountResponse; accountResponse = new AccountResponse("12345", BigDecimal.valueOf(1000), true, BigDecimal.valueOf(500));
        String accountId = "12345";
        ResponseEntity<AccountResponse> responseEntity = ResponseEntity.ok(accountResponse);
        when(restTemplate.exchange(anyString(), eq(GET), eq(null), eq(AccountResponse.class)))
                .thenReturn(responseEntity);

        Optional<AccountResponse> result = accountHttpClient.getAccountById(accountId);

        assertTrue(result.isPresent());
        assertEquals(accountResponse.getId(), result.get().getId());
        assertEquals(accountResponse.getBalance(), result.get().getBalance());
    }

    @Test
    void shouldReturnEmpty_whenHttpClientErrorExceptionOccurs() {
        String accountId = "12345";
        when(restTemplate.exchange(anyString(), eq(GET), eq(null), eq(AccountResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        Optional<AccountResponse> result = accountHttpClient.getAccountById(accountId);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldThrowException_whenRestClientExceptionOccurs() {
        String accountId = "12345";
        when(restTemplate.exchange(anyString(), eq(GET), eq(null), eq(AccountResponse.class)))
                .thenThrow(new RestClientException("Connection error"));

        assertThrows(RestClientException.class, () -> accountHttpClient.getAccountById(accountId));
    }

    @Test
    void shouldThrowApplicationGeneralError_whenUnknownExceptionOccurs() {
        String accountId = "12345";
        when(restTemplate.exchange(anyString(), eq(GET), eq(null), eq(AccountResponse.class)))
                .thenThrow(new RuntimeException("Unknown error"));

        assertThrows(ApplicationGeneralError.class, () -> accountHttpClient.getAccountById(accountId));
    }
}