package br.com.app.transferencia.adapters.outbound;

import br.com.app.transferencia.adapters.inbound.rest.request.TransferRequest;
import br.com.app.transferencia.adapters.inbound.rest.request.mapper.TransferMapper;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.exceptions.ApplicationErrorCode;
import br.com.app.transferencia.application.exceptions.TransferException;
import br.com.app.transferencia.application.ports.outbound.TransferenciaOutBoundPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class TransferAdapter implements TransferenciaOutBoundPort {
    private RestTemplate restTemplate;
    private TransferMapper mapper;

    @Value("${application.transferencias.url}")
    private String url;

    @Autowired
    public TransferAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.mapper = TransferMapper.INSTANCE;
    }

    @Override
    public void transfer(Transfer transfer) {
        log.info("Iniciando processo de transferência");
        HttpHeaders headers = buildRequestHeaders();
        TransferRequest transferRequest = mapper.mapToRequest(transfer);

        HttpEntity<TransferRequest> requestEntity = new HttpEntity<>(transferRequest, headers);

       try {
           ResponseEntity<TransferRequest> response = restTemplate.exchange(
                   this.url,
                   HttpMethod.PUT,
                   requestEntity,
                   TransferRequest.class
           );

           log.info("Chamada finalizada com status code: {}", response.getStatusCode().value());
           log.info("Transferência realizada com sucesso");
       } catch (RestClientException error) {
            log.error("Ocorreu um erro ao realizar a transferência");
            throw new TransferException(ApplicationErrorCode.TRANSFER_ERROR);
       }
    }

    private HttpHeaders buildRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}
