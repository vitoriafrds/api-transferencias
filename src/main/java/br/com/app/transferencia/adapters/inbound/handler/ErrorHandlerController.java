package br.com.app.transferencia.adapters.inbound.handler;

import br.com.app.transferencia.application.exceptions.ClienteNaoEncontradoException;
import br.com.app.transferencia.application.exceptions.ContaInativaException;
import br.com.app.transferencia.application.exceptions.LimiteDiarioInsuficienteException;
import br.com.app.transferencia.application.exceptions.SaldoInsuficienteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler({ClienteNaoEncontradoException.class})
    public ResponseEntity<ErrorWrapper> clienteNaoEncontradoException(ClienteNaoEncontradoException exception) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(exception.getErro().getCodigo());
        error.setMessage(exception.getMessage());

        return ResponseEntity.unprocessableEntity().body(new ErrorWrapper(error));
    }

    @ExceptionHandler({ContaInativaException.class})
    public ResponseEntity<ErrorWrapper> contaInativaException(ContaInativaException exception) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(exception.getErro().getCodigo());
        error.setMessage(exception.getMessage());

        return ResponseEntity.unprocessableEntity().body(new ErrorWrapper(error));
    }

    @ExceptionHandler({SaldoInsuficienteException.class})
    public ResponseEntity<ErrorWrapper> saldoInsuficienteException(SaldoInsuficienteException exception) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(exception.getErro().getCodigo());
        error.setMessage(exception.getMessage());

        return ResponseEntity.unprocessableEntity().body(new ErrorWrapper(error));
    }

    @ExceptionHandler({LimiteDiarioInsuficienteException.class})
    public ResponseEntity<ErrorWrapper> limiteDiarioInsufienteException(LimiteDiarioInsuficienteException exception) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(exception.getErro().getCodigo());
        error.setMessage(exception.getMessage());

        return ResponseEntity.unprocessableEntity().body(new ErrorWrapper(error));
    }
}
