package com.example.Clientes.infra.util;

import com.example.Clientes.domain.model.Cliente;
import com.example.Clientes.dto.request.ClienteRequest;
import com.example.Clientes.dto.response.ClienteResponse;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        cliente.setCpf(formatCpf(request.getCpf()));
        cliente.setTelefone(request.getTelefone());
        cliente.setEndereco(request.getEndereco());
        return cliente;
    }

    public void updateEntityFromRequest(Cliente cliente, ClienteRequest request) {
        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        cliente.setCpf(formatCpf(request.getCpf()));
        cliente.setTelefone(request.getTelefone());
        cliente.setEndereco(request.getEndereco());
    }

    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getDataCadastro()
        );
    }

    private String formatCpf(String cpf) {
        // Remove formatação se existir
        cpf = cpf.replaceAll("[^0-9]", "");

        // Adiciona formatação padrão XXX.XXX.XXX-XX
        if (cpf.length() == 11) {
            return cpf.substring(0, 3) + "." +
                    cpf.substring(3, 6) + "." +
                    cpf.substring(6, 9) + "-" +
                    cpf.substring(9, 11);
        }

        return cpf;
    }
}
