package br.com.app.transferencia.adapters.inbound.rest.handler;

import br.com.app.transferencia.application.exceptions.CustomerNotFoundException;
import br.com.app.transferencia.application.exceptions.ContaInativaException;
import br.com.app.transferencia.application.exceptions.LimiteDiarioInsuficienteException;
import br.com.app.transferencia.application.exceptions.SaldoInsuficienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<ErrorWrapper> clienteNaoEncontradoException(CustomerNotFoundException exception) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(exception.getErro().getCode());
        error.setMessage(exception.getMessage());

        return ResponseEntity.unprocessableEntity().body(new ErrorWrapper(error));
    }

    @ExceptionHandler({ContaInativaException.class})
    public ResponseEntity<ErrorWrapper> contaInativaException(ContaInativaException exception) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(exception.getErro().getCode());
        error.setMessage(exception.getMessage());

        return ResponseEntity.unprocessableEntity().body(new ErrorWrapper(error));
    }

    @ExceptionHandler({SaldoInsuficienteException.class})
    public ResponseEntity<ErrorWrapper> saldoInsuficienteException(SaldoInsuficienteException exception) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(exception.getErro().getCode());
        error.setMessage(exception.getMessage());

        return ResponseEntity.unprocessableEntity().body(new ErrorWrapper(error));
    }

    @ExceptionHandler({LimiteDiarioInsuficienteException.class})
    public ResponseEntity<ErrorWrapper> limiteDiarioInsufienteException(LimiteDiarioInsuficienteException exception) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(exception.getErro().getCode());
        error.setMessage(exception.getMessage());

        return ResponseEntity.unprocessableEntity().body(new ErrorWrapper(error));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException error) {
        Map<String, String> errors = new HashMap<>();

        error.getBindingResult().getFieldErrors().forEach(e ->
                errors.put(e.getField(), e.getDefaultMessage()));

        return errors;
    }
}
