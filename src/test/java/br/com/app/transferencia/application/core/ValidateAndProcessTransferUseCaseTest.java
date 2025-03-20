package br.com.app.transferencia.application.core;

import br.com.app.transferencia.application.core.domain.transferencia.Transfer;
import br.com.app.transferencia.application.core.domain.transferencia.TransferAccount;
import br.com.app.transferencia.application.core.usecase.ExecuteAndNotifyTransferUseCase;
import br.com.app.transferencia.application.core.usecase.ValidateAndProcessTransferUseCase;
import br.com.app.transferencia.application.core.validators.Validator;
import br.com.app.transferencia.application.exceptions.ApplicationGeneralError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ValidateAndProcessTransferUseCaseTest {
    private ValidateAndProcessTransferUseCase validateAndProcessTransferUseCase;

    @Mock
    private Validator<String> customerValidator;

    @Mock
    private Validator<Transfer> transferValidator;

    @Mock
    private ExecuteAndNotifyTransferUseCase transferenciaUseCase;

    @BeforeEach
    void setUp() {
        validateAndProcessTransferUseCase = new ValidateAndProcessTransferUseCase(
                this.customerValidator, this.transferValidator, this.transferenciaUseCase);
    }

    @Test
    void testExecuteWithValidTransfer() {
        TransferAccount account = new TransferAccount("123", "1231231");
        Transfer transfer = new Transfer("1", "123", new BigDecimal(100), account);

        validateAndProcessTransferUseCase.execute(transfer);

        verify(customerValidator, times(1)).validate(transfer.getCustomerId());
        verify(transferValidator, times(1)).validate(transfer);
        verify(transferenciaUseCase, times(1)).transfer(transfer);
    }
}