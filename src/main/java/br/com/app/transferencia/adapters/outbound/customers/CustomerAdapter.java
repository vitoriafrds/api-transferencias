package br.com.app.transferencia.adapters.outbound.customers;


import br.com.app.transferencia.adapters.outbound.customers.response.CustomerResponse;
import br.com.app.transferencia.application.ports.outbound.CustomerOutPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
public class CustomerAdapter implements CustomerOutPort {
    private RestTemplate restTemplate;

    @Value("${application.clientes.url}")
    private String url;

    @Autowired
    public CustomerAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<CustomerResponse> getCustomerById(String customerId) {
        var customerServiceUrl = formatUrl(customerId);

        try {
            ResponseEntity<CustomerResponse> cliente = this.restTemplate
                    .exchange(customerServiceUrl, GET, null, CustomerResponse.class);

            return Optional.of(cliente.getBody());
        } catch (HttpClientErrorException error) {
            log.error("Ocorreu um erro na tentativa de consultar o cliente: {}", customerId);
        }

        return Optional.empty();
    }

    private String formatUrl(String idCliente) {
        var placeHolderUrl =  this.url.concat("{id_cliente}");
        return placeHolderUrl.replace("{id_cliente}", idCliente);
    }
}
