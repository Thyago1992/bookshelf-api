package com.thyago.bookshelf_api.service;

import com.thyago.bookshelf_api.dto.LivroRequestDTO;
import com.thyago.bookshelf_api.dto.LivroResponseDTO;
import com.thyago.bookshelf_api.entity.Categoria;
import com.thyago.bookshelf_api.entity.Livro;
import com.thyago.bookshelf_api.enums.Edicao;
import com.thyago.bookshelf_api.exception.AlreadyExistsException;
import com.thyago.bookshelf_api.exception.ObjectNotFoundException;
import com.thyago.bookshelf_api.repository.CategoriaRepository;
import com.thyago.bookshelf_api.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ModelMapper modelMapper;

    // ======================== findAll ========================

    @Test
    void deveRetornarTodosLivros() {
        // --- ARRANGE ---
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        Livro livro = new Livro(1L, "Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, categoria);
        LivroResponseDTO dto = new LivroResponseDTO(1L, "Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, "Ficção Científica");

        Mockito.when(livroRepository.findAll()).thenReturn(List.of(livro));
        Mockito.when(modelMapper.map(livro, LivroResponseDTO.class)).thenReturn(dto);

        // --- ACT ---
        List<LivroResponseDTO> resultado = livroService.findAll();

        // --- ASSERT ---
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Duna", resultado.get(0).getNome());

        Mockito.verify(livroRepository, Mockito.times(1)).findAll();
    }

    // ======================== findById ========================

    @Test
    void deveRetornarLivroPorId() {
        // --- ARRANGE ---
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        Livro livro = new Livro(1L, "Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, categoria);
        LivroResponseDTO dto = new LivroResponseDTO(1L, "Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, "Ficção Científica");

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        Mockito.when(modelMapper.map(livro, LivroResponseDTO.class)).thenReturn(dto);

        // --- ACT ---
        LivroResponseDTO resultado = livroService.findById(1L);

        // --- ASSERT ---
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Duna", resultado.getNome());

        Mockito.verify(livroRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoEncontradoPorId() {
        // --- ARRANGE ---
        Mockito.when(livroRepository.findById(99L)).thenReturn(Optional.empty());

        // --- ACT + ASSERT ---
        assertThrows(ObjectNotFoundException.class, () -> livroService.findById(99L));

        Mockito.verify(livroRepository, Mockito.times(1)).findById(99L);
    }

    // ======================== save ========================

    @Test
    void deveSalvarLivro() {
        // --- ARRANGE ---
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        LivroRequestDTO request = new LivroRequestDTO("Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, 1L);
        Livro livroSalvo = new Livro(1L, "Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, categoria);
        LivroResponseDTO dto = new LivroResponseDTO(1L, "Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, "Ficção Científica");

        Mockito.when(livroRepository.existsByNomeIgnoreCaseAndAutorIgnoreCaseAndEdicao(
                "Duna", "Frank Herbert", Edicao.PRIMEIRA)).thenReturn(false);
        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        Mockito.when(livroRepository.save(Mockito.any(Livro.class))).thenReturn(livroSalvo);
        Mockito.when(modelMapper.map(livroSalvo, LivroResponseDTO.class)).thenReturn(dto);

        // --- ACT ---
        LivroResponseDTO resultado = livroService.save(request);

        // --- ASSERT ---
        assertNotNull(resultado);
        assertEquals("Duna", resultado.getNome());
        assertEquals("Frank Herbert", resultado.getAutor());

        Mockito.verify(livroRepository, Mockito.times(1)).save(Mockito.any(Livro.class));
    }

    @Test
    void deveLancarExcecaoAoSalvarLivroJaExistente() {
        // --- ARRANGE ---
        LivroRequestDTO request = new LivroRequestDTO("Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, 1L);

        Mockito.when(livroRepository.existsByNomeIgnoreCaseAndAutorIgnoreCaseAndEdicao(
                "Duna", "Frank Herbert", Edicao.PRIMEIRA)).thenReturn(true);

        // --- ACT + ASSERT ---
        assertThrows(AlreadyExistsException.class, () -> livroService.save(request));

        Mockito.verify(livroRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveLancarExcecaoAoSalvarLivroComCategoriaInexistente() {
        // --- ARRANGE ---
        LivroRequestDTO request = new LivroRequestDTO("Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, 99L);

        Mockito.when(livroRepository.existsByNomeIgnoreCaseAndAutorIgnoreCaseAndEdicao(
                "Duna", "Frank Herbert", Edicao.PRIMEIRA)).thenReturn(false);
        Mockito.when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        // --- ACT + ASSERT ---
        assertThrows(ObjectNotFoundException.class, () -> livroService.save(request));

        Mockito.verify(livroRepository, Mockito.never()).save(Mockito.any());
    }

    // ======================== update ========================

    @Test
    void deveAtualizarLivro() {
        // --- ARRANGE ---
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        LivroRequestDTO request = new LivroRequestDTO("Duna Messias", "Frank Herbert", "Continuação de Duna", Edicao.SEGUNDA, 1L);
        Livro livroExistente = new Livro(1L, "Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, categoria);
        Livro livroSalvo = new Livro(1L, "Duna Messias", "Frank Herbert", "Continuação de Duna", Edicao.SEGUNDA, categoria);
        LivroResponseDTO dto = new LivroResponseDTO(1L, "Duna Messias", "Frank Herbert", "Continuação de Duna", Edicao.SEGUNDA, "Ficção Científica");

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livroExistente));
        Mockito.when(livroRepository.existsByNomeIgnoreCaseAndAutorIgnoreCaseAndEdicaoAndIdNot(
                "Duna Messias", "Frank Herbert", Edicao.SEGUNDA, 1L)).thenReturn(false);
        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        Mockito.when(livroRepository.save(livroExistente)).thenReturn(livroSalvo);
        Mockito.when(modelMapper.map(livroSalvo, LivroResponseDTO.class)).thenReturn(dto);

        // --- ACT ---
        LivroResponseDTO resultado = livroService.update(1L, request);

        // --- ASSERT ---
        assertNotNull(resultado);
        assertEquals("Duna Messias", resultado.getNome());
        assertEquals(Edicao.SEGUNDA, resultado.getEdicao());

        Mockito.verify(livroRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(livroRepository, Mockito.times(1)).save(livroExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarLivroNaoEncontrado() {
        // --- ARRANGE ---
        LivroRequestDTO request = new LivroRequestDTO("Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, 1L);

        Mockito.when(livroRepository.findById(99L)).thenReturn(Optional.empty());

        // --- ACT + ASSERT ---
        assertThrows(ObjectNotFoundException.class, () -> livroService.update(99L, request));

        Mockito.verify(livroRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarLivroJaExistente() {
        // --- ARRANGE ---
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        LivroRequestDTO request = new LivroRequestDTO("Duna Messias", "Frank Herbert", "Continuação de Duna", Edicao.SEGUNDA, 1L);
        Livro livroExistente = new Livro(1L, "Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, categoria);

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livroExistente));
        Mockito.when(livroRepository.existsByNomeIgnoreCaseAndAutorIgnoreCaseAndEdicaoAndIdNot(
                "Duna Messias", "Frank Herbert", Edicao.SEGUNDA, 1L)).thenReturn(true);

        // --- ACT + ASSERT ---
        assertThrows(AlreadyExistsException.class, () -> livroService.update(1L, request));

        Mockito.verify(livroRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarLivroComCategoriaInexistente() {
        // --- ARRANGE ---
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        LivroRequestDTO request = new LivroRequestDTO("Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, 99L);
        Livro livroExistente = new Livro(1L, "Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, categoria);

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livroExistente));
        Mockito.when(livroRepository.existsByNomeIgnoreCaseAndAutorIgnoreCaseAndEdicaoAndIdNot(
                "Duna", "Frank Herbert", Edicao.PRIMEIRA, 1L)).thenReturn(false);
        Mockito.when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        // --- ACT + ASSERT ---
        assertThrows(ObjectNotFoundException.class, () -> livroService.update(1L, request));

        Mockito.verify(livroRepository, Mockito.never()).save(Mockito.any());
    }

    // ======================== delete ========================

    @Test
    void deveDeletarLivro() {
        // --- ARRANGE ---
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        Livro livro = new Livro(1L, "Duna", "Frank Herbert", "Uma epopeia galáctica", Edicao.PRIMEIRA, categoria);

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        // --- ACT ---
        livroService.delete(1L);

        // --- ASSERT ---
        Mockito.verify(livroRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(livroRepository, Mockito.times(1)).delete(livro);
    }

    @Test
    void deveLancarExcecaoAoDeletarLivroNaoEncontrado() {
        // --- ARRANGE ---
        Mockito.when(livroRepository.findById(99L)).thenReturn(Optional.empty());

        // --- ACT + ASSERT ---
        assertThrows(ObjectNotFoundException.class, () -> livroService.delete(99L));

        Mockito.verify(livroRepository, Mockito.never()).delete(Mockito.any());
    }
}