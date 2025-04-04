package com.example.Produtos.service;

import com.example.Produtos.domain.model.Produto;
import com.example.Produtos.domain.repository.ProdutoRepository;
import com.example.Produtos.dto.request.ProdutoRequest;
import com.example.Produtos.dto.response.ProdutoResponse;
import com.example.Produtos.infra.exception.ProdutoNotFoundException;
import com.example.Produtos.infra.util.ProdutoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public List<ProdutoResponse> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtoMapper.toResponseList(produtos);
    }

    public ProdutoResponse buscarPorId(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado com ID: " + id));

        return produtoMapper.toResponse(produto);
    }

    public List<ProdutoResponse> buscarPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoria(categoria);
        return produtoMapper.toResponseList(produtos);
    }

    public List<ProdutoResponse> buscarPorFabricacaoLocal(Boolean fabricadoEmMari) {
        List<Produto> produtos = produtoRepository.findByFabricadoEmMari(fabricadoEmMari);
        return produtoMapper.toResponseList(produtos);
    }

    public List<ProdutoResponse> buscarPorNome(String nome) {
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        return produtoMapper.toResponseList(produtos);
    }

    @Transactional
    public ProdutoResponse salvar(ProdutoRequest produtoRequest) {
        Produto produto = produtoMapper.toEntity(produtoRequest);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.toResponse(produtoSalvo);
    }

    @Transactional
    public ProdutoResponse atualizar(UUID id, ProdutoRequest produtoRequest) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado com ID: " + id));

        produtoMapper.updateEntityFromRequest(produto, produtoRequest);
        Produto produtoAtualizado = produtoRepository.save(produto);
        return produtoMapper.toResponse(produtoAtualizado);
    }

    @Transactional
    public void deletar(UUID id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNotFoundException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    @Transactional
    public ProdutoResponse atualizarEstoque(UUID id, Integer quantidade) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado com ID: " + id));

        produto.setQuantidadeEstoque(quantidade);
        Produto produtoAtualizado = produtoRepository.save(produto);
        return produtoMapper.toResponse(produtoAtualizado);
    }
}
