package br.com.app.transferencia.adapters.inbound;

import br.com.app.transferencia.adapters.inbound.rest.TransferenciaRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transferencias")
public class TransferenciaController {

    public ResponseEntity<Void> transferir(@RequestBody TransferenciaRequest transferencia) {
        //TODO: Implementar chamada do use case/service
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
