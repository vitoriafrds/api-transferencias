package br.com.app.transferencia.adapters;

import br.com.app.transferencia.adapters.inbound.TransferController;
import br.com.app.transferencia.application.core.usecase.ValidateAndProcessTransferUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TransferController.class)
public class TransferControllerTest {
    @MockitoBean
    private ValidateAndProcessTransferUseCase usecase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testTransferValidRequestShouldReturnCreated() throws Exception {
        String requestBody = """
             {
               "id_cliente": "2ceb26e9-7b5c-417e-bf75-ffaa66e3a76f",
               "valor": 20.00,
               "conta": {
                   "id_origem": "d0d32142-74b7-4aca-9c68-838aeacef96b",
                   "id_destino": "41313d7b-bd75-4c75-9dea-1f4be434007f"
               }
            }
        """;

        mockMvc.perform(post("/transferencias")
                        .contentType("application/json")
                        .content(requestBody)
                )
                .andExpect(status().isCreated());

        verify(usecase).execute(Mockito.any());
    }

    @Test
    void testTransferInvalidRequestShouldReturnBadRequest() throws Exception {
        String requestBody = """
             {
               "id_cliente": "2ceb26e9-7b5c-417e-bf75-ffaa66e3a76f",
               "valor": 0.00,
               "conta": {
                   "id_origem": "d0d32142-74b7-4aca-9c68-838aeacef96b",
                   "id_destino": "41313d7b-bd75-4c75-9dea-1f4be434007f"
               }
            }
        """;
        mockMvc.perform(post("/transferencias")
                        .contentType("application/json")
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest());
    }

}
