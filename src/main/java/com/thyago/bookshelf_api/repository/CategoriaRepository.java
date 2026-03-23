package com.thyago.bookshelf_api.repository;

import com.thyago.bookshelf_api.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findAllByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCase(String nome);

}
