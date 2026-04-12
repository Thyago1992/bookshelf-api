package com.thyago.bookshelf_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para requisição de criação ou atualização de categoria.")
public class CategoriaRequestDTO {

    @Schema(description = "O nome da categoria.", example = "Ficção Científica")
    @NotBlank(message = "O nome da categoria é obrigatório")
    private String nome;

    @Schema(description = "A descrição da categoria.", example = "Livros que exploram temas futuristas e avanços " +
            "tecnológicos.")
    @NotBlank(message = "O descrição da categoria é obrigatório")
    private String descricao;
}
