package com.thyago.bookshelf_api.dto;

import com.thyago.bookshelf_api.enums.Edicao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para resposta de detalhes de livro, incluindo o nome da categoria.")
public class LivroResponseDTO {

    @Schema(description = "O ID único do livro.", example = "1")
    private Long id;

    @Schema(description = "O nome do livro.", example = "O Senhor dos Anéis")
    private String nome;

    @Schema(description = "O autor do livro.", example = "J.R.R. Tolkien")
    private String autor;

    @Schema(description = "A sinópse do livro.", example = "Uma aventura épica na Terra Média.")
    private String sinopse;

    @Schema(description = "A edição do livro.", example = "PRIMEIRA_EDICAO")
    private Edicao edicao;

    @Schema(description = "O nome da categoria do livro.", example = "Ficção Científica")
    private String nomeCategoria;
}