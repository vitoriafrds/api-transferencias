package br.com.app.transferencia.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private String id;
    private String nome;
    private String telefone;
    private String tipoPessoa;
}
