package com.thyago.bookshelf_api.entity;

import com.thyago.bookshelf_api.enums.Edicao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false, columnDefinition = "TEXT")
    private  String sinopse;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Edicao edicao;

    @JoinColumn(name = "categoria_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;

    @Override
    public String toString() {
        return """
        Livro:
          ID:        %d
          Nome:      %s
          Autor:     %s
          Sinopse:   %s
          Edicao:    %s
          Categoria: %s
        """.formatted(id, nome, autor, sinopse, edicao,
                categoria != null ? categoria.getNome() : null
        );
    }
}
