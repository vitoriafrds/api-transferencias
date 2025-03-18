package br.com.app.transferencia.adapters.inbound.rest.handler;

import br.com.app.transferencia.application.exceptions.*;
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

    @ExceptionHandler({InactiveAccountException.class})
    public ResponseEntity<ErrorWrapper> contaInativaException(InactiveAccountException exception) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(exception.getErro().getCode());
        error.setMessage(exception.getMessage());

        return ResponseEntity.unprocessableEntity().body(new ErrorWrapper(error));
    }

    @ExceptionHandler({InsufficientBalanceException.class})
    public ResponseEntity<ErrorWrapper> saldoInsuficienteException(InsufficientBalanceException exception) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(exception.getErro().getCode());
        error.setMessage(exception.getMessage());

        return ResponseEntity.unprocessableEntity().body(new ErrorWrapper(error));
    }

    @ExceptionHandler({InsufficientDailyLimitException.class})
    public ResponseEntity<ErrorWrapper> limiteDiarioInsufienteException(InsufficientDailyLimitException exception) {
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

    @ExceptionHandler(ApplicationGeneralError.class)
    public ResponseEntity<ErrorWrapper> handleApplicationGeneralError(ApplicationGeneralError erro) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(erro.getErrorCode().getCode());
        error.setMessage(erro.getMessage());

        return ResponseEntity.internalServerError().body(new ErrorWrapper(error));
    }

    @ExceptionHandler(CustomerIntegrationException.class)
    public ResponseEntity<ErrorWrapper> handleCustomerIntegrationException(CustomerIntegrationException erro) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(erro.getCode().getCode());
        error.setMessage(erro.getMessage());

        return ResponseEntity.status(503).body(new ErrorWrapper(error));
    }

    @ExceptionHandler(AccountIntegrationException.class)
    public ResponseEntity<ErrorWrapper> handleAccountIntegrationException(AccountIntegrationException erro) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(erro.getErrorCode().getCode());
        error.setMessage(erro.getMessage());

        return ResponseEntity.status(503).body(new ErrorWrapper(error));
    }

    @ExceptionHandler(TransferIntegrationException.class)
    public ResponseEntity<ErrorWrapper> handleTransferIntegrationException(TransferIntegrationException erro) {
        Error error = new Error();
        error.setTimestamp(LocalDateTime.now());
        error.setCode(erro.getCode().getCode());
        error.setMessage(erro.getMessage());

        return ResponseEntity.status(503).body(new ErrorWrapper(error));
    }
}
