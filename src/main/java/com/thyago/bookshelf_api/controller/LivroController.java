package com.thyago.bookshelf_api.controller;

import com.thyago.bookshelf_api.dto.LivroRequestDTO;
import com.thyago.bookshelf_api.dto.LivroResponseDTO;
import com.thyago.bookshelf_api.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @Operation(description = "Recupera a lista de todos os livros cadastrados no sistema.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso.")})
    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> findAll() {
        List<LivroResponseDTO> list = livroService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(description = "Recupera os detalhes de um livro específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado e retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado para o ID fornecido.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> findById(@PathVariable Long id) {
        LivroResponseDTO livro = livroService.findById(id);
        return ResponseEntity.ok().body(livro);
    }

    @Operation(description = "Cria um novo livro no sistema com os dados fornecidos no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados fornecidos."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada para o ID fornecido."),
            @ApiResponse(responseCode = "409", description = "Conflito. Já existe um livro com o mesmo nome, autor e " +
                    "edição.")
    })
    @PostMapping
    public ResponseEntity<LivroResponseDTO> save(@Valid @RequestBody LivroRequestDTO livroRequestDTO) {
        LivroResponseDTO livro = livroService.save(livroRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(livro);
    }

    @Operation(description = "Exclui um livro existente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso."),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado para o ID fornecido.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livroService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Atualiza os dados de um livro existente com base no ID fornecido e nos novos dados no " +
            "corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados fornecidos."),
            @ApiResponse(responseCode = "404", description = "Livro ou categoria não encontrado para o ID fornecido."),
            @ApiResponse(responseCode = "409", description = "Conflito. Já existe um livro com o mesmo nome, autor e " +
                    "edição.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> update(@PathVariable Long id, @Valid @RequestBody LivroRequestDTO livroRequestDTO) {
        LivroResponseDTO atualizado = livroService.update(id, livroRequestDTO);
        return ResponseEntity.ok().body(atualizado);
    }


}
