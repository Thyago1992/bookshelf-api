package com.thyago.bookshelf_api.service;

import com.thyago.bookshelf_api.dto.LivroRequestDTO;
import com.thyago.bookshelf_api.dto.LivroResponseDTO;
import com.thyago.bookshelf_api.entity.Categoria;
import com.thyago.bookshelf_api.entity.Livro;
import com.thyago.bookshelf_api.exception.AlreadyExistsException;
import com.thyago.bookshelf_api.exception.ObjectNotFoundException;
import com.thyago.bookshelf_api.repository.CategoriaRepository;
import com.thyago.bookshelf_api.repository.LivroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final ModelMapper modelMapper;
    private final CategoriaRepository categoriaRepository;

    public LivroService(LivroRepository livroRepository, ModelMapper modelMapper, CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.modelMapper = modelMapper;
        this.categoriaRepository = categoriaRepository;
    }

    public LivroResponseDTO findById(Long id) {
        return livroRepository.findById(id)
                .map(livro -> modelMapper.map(livro, LivroResponseDTO.class))
                .orElseThrow(() -> new ObjectNotFoundException(ObjectNotFoundException.MSG_LIVRO_NAO_ENCONTRADO));
    }

    public List<LivroResponseDTO> findAll() {
        return livroRepository.findAll()
                .stream()
                .map(livro -> modelMapper.map(livro, LivroResponseDTO.class))
                .toList();
    }

    public LivroResponseDTO save(LivroRequestDTO dto) {
        if (livroRepository.existsByNomeIgnoreCaseAndAutorIgnoreCaseAndEdicao(
                dto.getNome(), dto.getAutor(), dto.getEdicao())) {
            throw new AlreadyExistsException(AlreadyExistsException.MSG_LIVRO_JA_EXISTE);
        }

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ObjectNotFoundException(ObjectNotFoundException.MSG_CATEGORIA_NAO_ENCONTRADA));

        Livro livro = new Livro();
        livro.setNome(dto.getNome());
        livro.setAutor(dto.getAutor());
        livro.setSinopse(dto.getSinopse());
        livro.setEdicao(dto.getEdicao());
        livro.setCategoria(categoria);

        Livro salvo = livroRepository.save(livro);
        return modelMapper.map(salvo, LivroResponseDTO.class);
    }

    public LivroResponseDTO update(Long id, LivroRequestDTO dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ObjectNotFoundException.MSG_LIVRO_NAO_ENCONTRADO));

        if (livroRepository.existsByNomeIgnoreCaseAndAutorIgnoreCaseAndEdicaoAndIdNot(
                dto.getNome(), dto.getAutor(), dto.getEdicao(), id)) {
            throw new AlreadyExistsException(AlreadyExistsException.MSG_LIVRO_JA_EXISTE);
        }

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ObjectNotFoundException(ObjectNotFoundException.MSG_CATEGORIA_NAO_ENCONTRADA));

        livro.setNome(dto.getNome());
        livro.setAutor(dto.getAutor());
        livro.setSinopse(dto.getSinopse());
        livro.setEdicao(dto.getEdicao());
        livro.setCategoria(categoria);

        Livro salvo = livroRepository.save(livro);
        return modelMapper.map(salvo, LivroResponseDTO.class);
    }

     public void delete(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ObjectNotFoundException.MSG_LIVRO_NAO_ENCONTRADO));
        livroRepository.delete(livro);
    }

}
