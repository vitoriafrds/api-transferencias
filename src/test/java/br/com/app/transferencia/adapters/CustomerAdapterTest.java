package br.com.app.transferencia.adapters;

import br.com.app.transferencia.adapters.outbound.customers.CustomerAdapter;
import br.com.app.transferencia.adapters.outbound.customers.client.CustomerHttpClient;
import br.com.app.transferencia.adapters.outbound.customers.response.CustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerAdapterTest {
    @Mock
    private CustomerHttpClient clientMock;

    private CustomerAdapter adapter;

    @BeforeEach
    public void setup() {
        this.adapter = new CustomerAdapter(this.clientMock);
    }

    @Test
    void testDeveConsultarClienteCorretamente() {
        when(clientMock.getCustomerById(anyString())).thenReturn(Optional.of(
                new CustomerResponse("1", "name", "03123131", "PJ")
        ));

        Optional<CustomerResponse> customer = this.adapter.getCustomerById("1");

        assertNotNull(customer.get());
    }

}
