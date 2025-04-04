package com.example.Produtos.domain.repository;

import com.example.Produtos.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    List<Produto> findByCategoria(String categoria);

    List<Produto> findByFabricadoEmMari(Boolean fabricadoEmMari);

    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
