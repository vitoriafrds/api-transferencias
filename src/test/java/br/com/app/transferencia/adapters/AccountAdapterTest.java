package br.com.app.transferencia.adapters;

import br.com.app.transferencia.adapters.outbound.accounts.AccountAdapter;
import br.com.app.transferencia.adapters.outbound.accounts.client.AccountHttpClient;
import br.com.app.transferencia.adapters.outbound.accounts.response.AccountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class AccountAdapterTest {
    private AccountAdapter adapter;

    @Mock
    private  AccountHttpClient accountClientMock;

    @BeforeEach
    public void setup() {
        this.adapter = new AccountAdapter(this.accountClientMock);
    }

    @Test
    void testDeveConsultarContasCorretamente() {
        Mockito.when(accountClientMock.getAccountById(Mockito.anyString()))
                .thenReturn(Optional.of(new AccountResponse("1", BigDecimal.valueOf(200), true, BigDecimal.valueOf(500))));

        Optional<AccountResponse> account = adapter.getAccountById("123");

        assertNotNull(account.get());
    }
}
