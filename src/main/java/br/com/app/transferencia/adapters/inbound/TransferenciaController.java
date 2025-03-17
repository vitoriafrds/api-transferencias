package br.com.app.transferencia.adapters.inbound;

import br.com.app.transferencia.adapters.inbound.request.TransferenciaRequest;
import br.com.app.transferencia.adapters.inbound.request.mapper.TransferenciaMapper;
import br.com.app.transferencia.application.core.usecase.ProcessarTransferenciaUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transferencias")
public class TransferenciaController {
    private TransferenciaMapper mapper;
    private ProcessarTransferenciaUseCase usecase;

    @Autowired
    public TransferenciaController(ProcessarTransferenciaUseCase usecase) {
        this.usecase = usecase;
        this.mapper = TransferenciaMapper.INSTANCE;
    }

    @PostMapping
    public ResponseEntity<Void> transferir(@RequestBody TransferenciaRequest transferencia) {
        this.usecase.executar(this.mapper.mapToDomain(transferencia));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
