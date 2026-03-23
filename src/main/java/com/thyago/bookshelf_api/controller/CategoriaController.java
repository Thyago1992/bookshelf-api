package com.thyago.bookshelf_api.controller;

import com.thyago.bookshelf_api.dto.CategoriaRequestDTO;
import com.thyago.bookshelf_api.dto.CategoriaResponseDTO;
import com.thyago.bookshelf_api.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(description = "Recupera a lista de todas as categorias cadastradas no sistema.")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso."))
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> findAll() {
        List<CategoriaResponseDTO> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @Operation(description = "Recupera os detalhes de uma categoria específica com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada e retornada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada para o ID fornecido.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        CategoriaResponseDTO categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @Operation(description = "Recupera a lista de categorias que correspondem ao nome fornecido, ignorando diferenças " +
            "de maiúsculas e minúsculas.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso.")})
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<CategoriaResponseDTO>> findByNome(@PathVariable String nome) {
        List<CategoriaResponseDTO> categorias = categoriaService.findAllByNomeIgnoreCase(nome);
        return ResponseEntity.ok(categorias);
    }

    @Operation(description = "Cria uma nova categoria no sistema com os dados fornecidos no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados fornecidos."),
            @ApiResponse(responseCode = "409", description = "Conflito. O nome da categoria fornecido já está em uso " +
                    "por outra categoria.")
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> save(@Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO categoria = categoriaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @Operation(description = "Exclui uma categoria existente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada para o ID fornecido.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Atualiza os dados de uma categoria existente com base no ID fornecido e nos novos dados " +
            "no corpo da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados fornecidos."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada para o ID fornecido.")})
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO atualizado = categoriaService.update(id, dto);
        return ResponseEntity.ok(atualizado);
    }

}
