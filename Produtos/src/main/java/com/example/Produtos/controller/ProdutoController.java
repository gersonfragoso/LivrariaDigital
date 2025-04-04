package com.example.Produtos.controller;

import com.example.Produtos.dto.request.ProdutoRequest;
import com.example.Produtos.dto.response.ProdutoResponse;
import com.example.Produtos.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listarTodos() {
        List<ProdutoResponse> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable UUID id) {
        ProdutoResponse produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponse>> buscarPorCategoria(@PathVariable String categoria) {
        List<ProdutoResponse> produtos = produtoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/fabricacao-local")
    public ResponseEntity<List<ProdutoResponse>> buscarPorFabricacaoLocal(
            @RequestParam(name = "local", defaultValue = "true") Boolean fabricadoEmMari) {
        List<ProdutoResponse> produtos = produtoService.buscarPorFabricacaoLocal(fabricadoEmMari);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/busca")
    public ResponseEntity<List<ProdutoResponse>> buscarPorNome(@RequestParam String nome) {
        List<ProdutoResponse> produtos = produtoService.buscarPorNome(nome);
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest produtoRequest) {
        ProdutoResponse produtoCriado = produtoService.salvar(produtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ProdutoRequest produtoRequest) {
        ProdutoResponse produtoAtualizado = produtoService.atualizar(id, produtoRequest);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<ProdutoResponse> atualizarEstoque(
            @PathVariable UUID id,
            @RequestParam Integer quantidade) {
        ProdutoResponse produtoAtualizado = produtoService.atualizarEstoque(id, quantidade);
        return ResponseEntity.ok(produtoAtualizado);
    }
}
