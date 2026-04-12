package com.thyago.bookshelf_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para resposta de detalhes de categoria.")
public class CategoriaResponseDTO {

    @Schema(description = "O ID único da categoria.", example = "1")
    private Long id;

    @Schema(description = "O nome da categoria.", example = "Ficção Científica")
    private String nome;

    @Schema(description = "A descrição da categoria.", example = "Livros que exploram temas futuristas e avanços " +
            "tecnológicos.")
    private String descricao;
}
