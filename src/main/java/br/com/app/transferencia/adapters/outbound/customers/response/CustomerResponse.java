package br.com.app.transferencia.adapters.outbound.customers.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("telefone")
    private String telephoneNumber;

    @JsonProperty("tipoPessoa")
    private String personType;
}
