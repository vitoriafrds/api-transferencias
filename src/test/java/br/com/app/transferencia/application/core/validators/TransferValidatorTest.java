package br.com.app.transferencia.application.core.validators;

import br.com.app.transferencia.application.core.domain.conta.Account;
import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.core.domain.transferencia.TransferAccount;
import br.com.app.transferencia.application.core.usecase.GetAccountUseCase;
import br.com.app.transferencia.application.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferValidatorTest {
    private TransferValidator transferValidator;
    @Mock
    private GetAccountUseCase contaUseCase;

    @BeforeEach
    void setUp() {
        transferValidator = new TransferValidator(this.contaUseCase);
    }

    @Test
    void testDeveValidarTransferenciaComSucesso() {
        Transfer transfer = new Transfer("", "customerId", new BigDecimal("1000.00"), new TransferAccount("1545", "destinationAccountId"));
        Account account = new Account("1545", new BigDecimal("2000.00"), true, new BigDecimal("5000.00"));

        when(contaUseCase.getAccountById("1545")).thenReturn(Optional.of(account));

        assertDoesNotThrow(() -> transferValidator.validate(transfer));
    }

    @Test
    void testDeveFalharSeContaNaoExistir() {
        Transfer transfer = new Transfer("", "customerId", new BigDecimal("1000.00"), new TransferAccount("1545", "destinationAccountId"));

        when(contaUseCase.getAccountById("1545")).thenReturn(Optional.empty());

        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> transferValidator.validate(transfer));
        assertEquals(ApplicationErrorCode.NON_EXISTENT_ACCOUNT, exception.getErro());
    }

    @Test
    void testDeveFalharSeContaInativa() {
        Transfer transfer = new Transfer("", "customerId", new BigDecimal("1000.00"), new TransferAccount("1545", "destinationAccountId"));
        Account account = new Account("1545", new BigDecimal("2000.00"), false, new BigDecimal("5000.00"));

        when(contaUseCase.getAccountById("1545")).thenReturn(Optional.of(account));

        InactiveAccountException exception = assertThrows(InactiveAccountException.class, () -> transferValidator.validate(transfer));
        assertEquals(ApplicationErrorCode.INACTIVE_ACCOUNT, exception.getErro());
    }

    @Test
    void testDeveFalharSeSaldoInsuficiente() {
        Transfer transfer = new Transfer("", "customerId", new BigDecimal("5000.00"), new TransferAccount("1545", "destinationAccountId"));
        Account account = new Account("1545", new BigDecimal("1000.00"), true, new BigDecimal("5000.00"));

        when(contaUseCase.getAccountById("1545")).thenReturn(Optional.of(account));

        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> transferValidator.validate(transfer));
        assertEquals(ApplicationErrorCode.INSUFFICIENT_BALANCE, exception.getErro());
    }

    @Test
    void testDeveFalharSeLimiteDiarioInsuficiente() {
        Transfer transfer = new Transfer("", "customerId", new BigDecimal("5000.00"), new TransferAccount("1545", "destinationAccountId"));
        Account account = new Account("1545", new BigDecimal("10000.00"), true, new BigDecimal("1000.00"));

        when(contaUseCase.getAccountById("1545")).thenReturn(Optional.of(account));

        InsufficientDailyLimitException exception = assertThrows(InsufficientDailyLimitException.class, () -> transferValidator.validate(transfer));
        assertEquals(ApplicationErrorCode.INSUFFICIENT_DAILY_LIMIT, exception.getErro());
    }
}
