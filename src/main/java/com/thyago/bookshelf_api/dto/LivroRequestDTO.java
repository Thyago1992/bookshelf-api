package com.thyago.bookshelf_api.dto;

import com.thyago.bookshelf_api.enums.Edicao;
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
public class LivroRequestDTO {

    @NotBlank(message = "O nome do livro é obrigatório")
    private String nome;

    @NotBlank(message = "O autor do livro é obrigatório")
    private String autor;

    @NotBlank(message = "A sinópse do livro é obrigatória")
    private String sinopse;

    @NotNull(message = "A edição do livro é obrigatória")
    private Edicao edicao;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Long idCategoria;
}
