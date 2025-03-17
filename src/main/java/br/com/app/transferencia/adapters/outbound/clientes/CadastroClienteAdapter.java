package br.com.app.transferencia.adapters.outbound.clientes;


import br.com.app.transferencia.adapters.outbound.clientes.response.ClienteResponse;
import br.com.app.transferencia.application.ports.CadastroClientesOutPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
public class CadastroClienteAdapter implements CadastroClientesOutPort {
    private RestTemplate restTemplate;

    @Value("${application.clientes.url}")
    private String url;

    @Autowired
    public CadastroClienteAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<ClienteResponse> consultarCliente(String idCliente) {
        var urlServicoCliente = formatarUrl(idCliente);

        try {
            ResponseEntity<ClienteResponse> cliente = this.restTemplate
                    .exchange(urlServicoCliente, GET, null, ClienteResponse.class);

            return Optional.of(cliente.getBody());
        } catch (HttpClientErrorException error) {
            log.error("Um erro ocorreu");
        }

        return Optional.empty();
    }

    private String formatarUrl(String idCliente) {
        //TODO: Criar uma classe de configuracao para esse dado
        var placeHolderUrl =  this.url.concat("{id_cliente}");
        return placeHolderUrl.replace("{id_cliente}", idCliente);
    }
}
