package br.com.app.transferencia.adapters.outbound.accounts;

import br.com.app.transferencia.adapters.outbound.accounts.response.AccountResponse;
import br.com.app.transferencia.application.ports.outbound.AccountOutPort;
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
public class AccountAdapter implements AccountOutPort {
    private RestTemplate restTemplate;

    @Value("${application.contas.url}")
    private String url;

    public AccountAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<AccountResponse> getAccountById(String accountId) {
        var accountServiceUrl = formatUrl(accountId);

        try {
            ResponseEntity<AccountResponse> conta = this.restTemplate
                    .exchange(accountServiceUrl, GET, null, AccountResponse.class);

            return Optional.of(conta.getBody());
        } catch (HttpClientErrorException error) {
            log.error("Ocorreu um erro ao consultar a conta: {}", accountId);
        }

        return Optional.empty();
    }

    private String formatUrl(String accountId) {
        //TODO: Criar uma classe de configuracao para esse dado
        var placeHolderUrl =  this.url.concat("{id_conta}");
        return placeHolderUrl.replace("{id_conta}", accountId);
    }
}
