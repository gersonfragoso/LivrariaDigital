package com.example.Clientes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private UUID id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private String endereco;
    private LocalDateTime dataCadastro;
}
