package br.com.app.transferencia.adapters.outbound.contas;

import br.com.app.transferencia.adapters.outbound.clientes.response.ClienteResponse;
import br.com.app.transferencia.adapters.outbound.contas.response.ContaResponse;
import br.com.app.transferencia.application.ports.ContaOutPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
public class ContaAdapter implements ContaOutPort {
    private RestTemplate restTemplate;

    @Value("${application.contas.url}")
    private String url;

    public ContaAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<ContaResponse> consultarConta(String idConta) {
        var urlServicoConta = formatarUrl(idConta);

        try {
            ResponseEntity<ContaResponse> conta = this.restTemplate
                    .exchange(urlServicoConta, GET, null, ContaResponse.class);

            return Optional.of(conta.getBody());
        } catch (HttpClientErrorException error) {
            log.error("A conta não existe");
        }

        return Optional.empty();
    }

    private String formatarUrl(String idConta) {
        //TODO: Criar uma classe de configuracao para esse dado
        var placeHolderUrl =  this.url.concat("{id_conta}");
        return placeHolderUrl.replace("{id_conta}", idConta);
    }
}
