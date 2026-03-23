package com.thyago.bookshelf_api.service;

import com.thyago.bookshelf_api.dto.CategoriaRequestDTO;
import com.thyago.bookshelf_api.dto.CategoriaResponseDTO;
import com.thyago.bookshelf_api.entity.Categoria;
import com.thyago.bookshelf_api.exception.AlreadyExistsException;
import com.thyago.bookshelf_api.exception.ObjectNotFoundException;
import com.thyago.bookshelf_api.repository.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private final ModelMapper modelMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoriaResponseDTO> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaResponseDTO.class))
                .toList();
    }

    public CategoriaResponseDTO findById(Long id) {
        return categoriaRepository.findById(id)
                .map(categoria -> modelMapper.map(categoria, CategoriaResponseDTO.class))
                .orElseThrow(() -> new ObjectNotFoundException(ObjectNotFoundException.MSG_CATEGORIA_NAO_ENCONTRADA));
    }

    public List<CategoriaResponseDTO> findAllByNomeIgnoreCase(String nome) {
        return categoriaRepository.findAllByNomeIgnoreCase(nome)
                .stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaResponseDTO.class))
                .toList();
    }

    public CategoriaResponseDTO save(CategoriaRequestDTO dto) {
        if(categoriaRepository.existsByNomeIgnoreCase(dto.getNome())){
            throw new AlreadyExistsException(AlreadyExistsException.MSG_CATEGORIA_JA_EXISTE);
        }
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categoria salvo = categoriaRepository.save(categoria);
        return modelMapper.map(salvo, CategoriaResponseDTO.class);
    }

    public void deleteById(Long id) {
        categoriaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ObjectNotFoundException.MSG_CATEGORIA_NAO_ENCONTRADA));
        categoriaRepository.deleteById(id);
    }

    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ObjectNotFoundException.MSG_CATEGORIA_NAO_ENCONTRADA));
        if(!categoria.getNome().equals(dto.getNome()) && categoriaRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new AlreadyExistsException(AlreadyExistsException.MSG_CATEGORIA_JA_EXISTE);
        }
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        Categoria salvo = categoriaRepository.save(categoria);
        return modelMapper.map(salvo, CategoriaResponseDTO.class);
    }

}
