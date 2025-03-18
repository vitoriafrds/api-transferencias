package br.com.app.transferencia.adapters.inbound;

import br.com.app.transferencia.adapters.inbound.request.TransferRequest;
import br.com.app.transferencia.adapters.inbound.request.mapper.TransferMapper;
import br.com.app.transferencia.application.core.usecase.ValidateAndProcessTransferUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transferencias")
public class TransferController {
    private TransferMapper mapper;
    private ValidateAndProcessTransferUseCase usecase;

    @Autowired
    public TransferController(ValidateAndProcessTransferUseCase usecase) {
        this.usecase = usecase;
        this.mapper = TransferMapper.INSTANCE;
    }

    @PostMapping
    public ResponseEntity<Void> transfer(@RequestBody TransferRequest transfer) {
        this.usecase.execute(this.mapper.mapToDomain(transfer));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
