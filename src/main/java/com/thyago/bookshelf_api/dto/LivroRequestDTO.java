package com.thyago.bookshelf_api.dto;

import com.thyago.bookshelf_api.enums.Edicao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para requisição de criação ou atualização de livro.")
public class LivroRequestDTO {

    @Schema(description = "O nome do livro.", example = "O Senhor dos Anéis")
    @NotBlank(message = "O nome do livro é obrigatório")
    private String nome;

    @Schema(description = "O autor do livro.", example = "J.R.R. Tolkien")
    @NotBlank(message = "O autor do livro é obrigatório")
    private String autor;

    @Schema(description = "A sinópse do livro.", example = "Uma aventura épica na Terra Média.")
    @NotBlank(message = "A sinópse do livro é obrigatória")
    private String sinopse;

    @Schema(description = "A edição do livro.", example = "PRIMEIRA_EDICAO")
    @NotNull(message = "A edição do livro é obrigatória")
    private Edicao edicao;

    @Schema(description = "O ID da categoria do livro.", example = "1")
    @NotNull(message = "O ID da categoria é obrigatório")
    private Long idCategoria;
}
