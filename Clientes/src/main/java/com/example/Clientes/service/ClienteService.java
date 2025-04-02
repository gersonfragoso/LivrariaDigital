package com.example.Clientes.service;


import com.example.Clientes.domain.model.Cliente;
import com.example.Clientes.domain.repository.ClienteRepository;
import com.example.Clientes.dto.request.ClienteRequest;
import com.example.Clientes.dto.response.ClienteResponse;
import com.example.Clientes.infra.exception.BusinessException;
import com.example.Clientes.infra.exception.ResourceNotFoundException;
import com.example.Clientes.infra.util.ClienteMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public Page<ClienteResponse> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(clienteMapper::toResponse);
    }

    public ClienteResponse findById(UUID id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    @Transactional
    public ClienteResponse create(ClienteRequest request) {
        validateClienteUniqueness(request);

        Cliente cliente = clienteMapper.toEntity(request);
        Cliente savedCliente = clienteRepository.save(cliente);

        return clienteMapper.toResponse(savedCliente);
    }

    @Transactional
    public ClienteResponse update(UUID id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));

        validateClienteUniquenessForUpdate(id, request);

        clienteMapper.updateEntityFromRequest(cliente, request);
        Cliente updatedCliente = clienteRepository.save(cliente);

        return clienteMapper.toResponse(updatedCliente);
    }

    @Transactional
    public void delete(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com o ID: " + id);
        }

        clienteRepository.deleteById(id);
    }

    public ClienteResponse findByEmail(String email) {
        return clienteRepository.findByEmail(email)
                .map(clienteMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o email: " + email));
    }

    public ClienteResponse findByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .map(clienteMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o CPF: " + cpf));
    }

    private void validateClienteUniqueness(ClienteRequest request) {
        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Já existe um cliente cadastrado com este email");
        }

        if (clienteRepository.existsByCpf(request.getCpf())) {
            throw new BusinessException("Já existe um cliente cadastrado com este CPF");
        }
    }

    private void validateClienteUniquenessForUpdate(UUID id, ClienteRequest request) {
        if (clienteRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new BusinessException("Já existe um cliente cadastrado com este email");
        }

        if (clienteRepository.existsByCpfAndIdNot(request.getCpf(), id)) {
            throw new BusinessException("Já existe um cliente cadastrado com este CPF");
        }
    }
}
