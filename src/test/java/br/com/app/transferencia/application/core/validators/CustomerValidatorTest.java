package br.com.app.transferencia.application.core.validators;

import br.com.app.transferencia.application.core.domain.cliente.Customer;
import br.com.app.transferencia.application.core.usecase.GetCustomerUseCase;
import br.com.app.transferencia.application.exceptions.ApplicationErrorCode;
import br.com.app.transferencia.application.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerValidatorTest {
    private CustomerValidator customerValidator;
    @Mock
    private GetCustomerUseCase useCaseMock;

    @BeforeEach
    void setUp() {
       this.customerValidator = new CustomerValidator(this.useCaseMock);
    }

    @Test
    void testDeveValidarClienteExistente() {
        var id = "12313";
        Customer mockCustomer = new Customer(id, "John Doe", "123456789", "Individual");
        when(useCaseMock.getCustomerById(id)).thenReturn(Optional.of(mockCustomer));

        customerValidator.validate(id);

        verify(useCaseMock, times(1)).getCustomerById(id);
    }

    @Test
    void testDeveLancarExceptionSeClienteNaoExistir() {
        var id = "000";
        when(useCaseMock.getCustomerById(id)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerValidator.validate(id);
        });

        assertEquals(ApplicationErrorCode.CUSTOMER_NOT_FOUND, exception.getErro());
        verify(useCaseMock, times(1)).getCustomerById(id);
    }
}
