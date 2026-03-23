package com.thyago.bookshelf_api.dto;

import com.thyago.bookshelf_api.enums.Edicao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LivroResponseDTO {

    private Long id;
    private String nome;
    private String autor;
    private String sinopse;
    private Edicao edicao;
    private String nomeCategoria;
}