package com.example.Produtos.infra.util;

import com.example.Produtos.domain.model.Produto;
import com.example.Produtos.dto.request.ProdutoRequest;
import com.example.Produtos.dto.response.ProdutoResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequest request) {
        if (request == null) {
            return null;
        }

        Produto produto = new Produto();
        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        produto.setCategoria(request.getCategoria());
        produto.setFabricadoEmMari(request.getFabricadoEmMari());
        produto.setQuantidadeEstoque(request.getQuantidadeEstoque());

        return produto;
    }

    public void updateEntityFromRequest(Produto produto, ProdutoRequest request) {
        if (produto == null || request == null) {
            return;
        }

        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        produto.setCategoria(request.getCategoria());
        produto.setFabricadoEmMari(request.getFabricadoEmMari());
        produto.setQuantidadeEstoque(request.getQuantidadeEstoque());
    }

    public ProdutoResponse toResponse(Produto produto) {
        if (produto == null) {
            return null;
        }

        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getCategoria(),
                produto.getFabricadoEmMari(),
                produto.getQuantidadeEstoque()
        );
    }


    public List<ProdutoResponse> toResponseList(List<Produto> produtos) {
        if (produtos == null) {
            return null;
        }

        return produtos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}