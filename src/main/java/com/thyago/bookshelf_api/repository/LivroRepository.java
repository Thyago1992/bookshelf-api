package com.thyago.bookshelf_api.repository;

import com.thyago.bookshelf_api.entity.Livro;
import com.thyago.bookshelf_api.enums.Edicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    boolean existsByNomeIgnoreCaseAndAutorIgnoreCaseAndEdicao(String nome, String autor, Edicao edicao);
    boolean existsByNomeIgnoreCaseAndAutorIgnoreCaseAndEdicaoAndIdNot(String nome, String autor, Edicao edicao, Long id);
}
