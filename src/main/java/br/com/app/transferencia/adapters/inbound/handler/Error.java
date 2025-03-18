package br.com.app.transferencia.adapters.inbound.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private String code;
    private String message;
    private LocalDateTime timestamp;
}
