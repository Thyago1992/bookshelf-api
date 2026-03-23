package com.thyago.bookshelf_api.service;

import com.thyago.bookshelf_api.dto.CategoriaRequestDTO;
import com.thyago.bookshelf_api.dto.CategoriaResponseDTO;
import com.thyago.bookshelf_api.entity.Categoria;
import com.thyago.bookshelf_api.exception.AlreadyExistsException;
import com.thyago.bookshelf_api.exception.ObjectNotFoundException;
import com.thyago.bookshelf_api.repository.CategoriaRepository;
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
class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ModelMapper modelMapper;

    // ======================== findAll ========================

    @Test
    void deveRetornarTodasCategorias() {
        // --- ARRANGE ---
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        CategoriaResponseDTO dto = new CategoriaResponseDTO(1L, "Ficção Científica", "Livros de ficção científica");

        Mockito.when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
        Mockito.when(modelMapper.map(categoria, CategoriaResponseDTO.class)).thenReturn(dto);

        // --- ACT ---
        List<CategoriaResponseDTO> resultado = categoriaService.findAll();

        // --- ASSERT ---
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ficção Científica", resultado.get(0).getNome());

        Mockito.verify(categoriaRepository, Mockito.times(1)).findAll();
    }

    // ======================== findById ========================

    @Test
    void deveRetornarCategoriaPorId() {
        // --- ARRANGE ---
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        CategoriaResponseDTO dto = new CategoriaResponseDTO(1L, "Ficção Científica", "Livros de ficção científica");

        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        Mockito.when(modelMapper.map(categoria, CategoriaResponseDTO.class)).thenReturn(dto);

        // --- ACT ---
        CategoriaResponseDTO resultado = categoriaService.findById(1L);

        // --- ASSERT ---
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Ficção Científica", resultado.getNome());

        Mockito.verify(categoriaRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontradaPorId() {
        // --- ARRANGE ---
        Mockito.when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        // --- ACT + ASSERT ---
        assertThrows(ObjectNotFoundException.class, () -> categoriaService.findById(99L));

        Mockito.verify(categoriaRepository, Mockito.times(1)).findById(99L);
    }

    // ======================== findAllByNomeIgnoreCase ========================

    @Test
    void deveRetornarCategoriasPorNome() {
        // --- ARRANGE ---
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        CategoriaResponseDTO dto = new CategoriaResponseDTO(1L, "Ficção Científica", "Livros de ficção científica");

        Mockito.when(categoriaRepository.findAllByNomeIgnoreCase("ficção científica")).thenReturn(List.of(categoria));
        Mockito.when(modelMapper.map(categoria, CategoriaResponseDTO.class)).thenReturn(dto);

        // --- ACT ---
        List<CategoriaResponseDTO> resultado = categoriaService.findAllByNomeIgnoreCase("ficção científica");

        // --- ASSERT ---
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ficção Científica", resultado.get(0).getNome());

        Mockito.verify(categoriaRepository, Mockito.times(1)).findAllByNomeIgnoreCase("ficção científica");
    }

    // ======================== save ========================

    @Test
    void deveSalvarCategoria() {
        // --- ARRANGE ---
        CategoriaRequestDTO request = new CategoriaRequestDTO("Ficção Científica", "Livros de ficção científica");
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        CategoriaResponseDTO dto = new CategoriaResponseDTO(1L, "Ficção Científica", "Livros de ficção científica");

        Mockito.when(categoriaRepository.existsByNomeIgnoreCase("Ficção Científica")).thenReturn(false);
        Mockito.when(categoriaRepository.save(Mockito.any(Categoria.class))).thenReturn(categoria);
        Mockito.when(modelMapper.map(categoria, CategoriaResponseDTO.class)).thenReturn(dto);

        // --- ACT ---
        CategoriaResponseDTO resultado = categoriaService.save(request);

        // --- ASSERT ---
        assertNotNull(resultado);
        assertEquals("Ficção Científica", resultado.getNome());

        Mockito.verify(categoriaRepository, Mockito.times(1)).existsByNomeIgnoreCase("Ficção Científica");
        Mockito.verify(categoriaRepository, Mockito.times(1)).save(Mockito.any(Categoria.class));
    }

    @Test
    void deveLancarExcecaoAoSalvarCategoriaComNomeDuplicado() {
        // --- ARRANGE ---
        CategoriaRequestDTO request = new CategoriaRequestDTO("Ficção Científica", "Livros de ficção científica");

        Mockito.when(categoriaRepository.existsByNomeIgnoreCase("Ficção Científica")).thenReturn(true);

        // --- ACT + ASSERT ---
        assertThrows(AlreadyExistsException.class, () -> categoriaService.save(request));

        Mockito.verify(categoriaRepository, Mockito.never()).save(Mockito.any());
    }

    // ======================== deleteById ========================

    @Test
    void deveDeletarCategoria() {
        // --- ARRANGE ---
        Categoria categoria = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");

        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        // --- ACT ---
        categoriaService.deleteById(1L);

        // --- ASSERT ---
        Mockito.verify(categoriaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(categoriaRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarCategoriaNaoEncontrada() {
        // --- ARRANGE ---
        Mockito.when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        // --- ACT + ASSERT ---
        assertThrows(ObjectNotFoundException.class, () -> categoriaService.deleteById(99L));

        Mockito.verify(categoriaRepository, Mockito.never()).deleteById(Mockito.any());
    }

    // ======================== update ========================

    @Test
    void deveAtualizarCategoria() {
        // --- ARRANGE ---
        CategoriaRequestDTO request = new CategoriaRequestDTO("Terror", "Livros de terror");
        Categoria categoriaExistente = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        Categoria categoriaSalva = new Categoria(1L, "Terror", "Livros de terror");
        CategoriaResponseDTO dto = new CategoriaResponseDTO(1L, "Terror", "Livros de terror");

        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaExistente));
        Mockito.when(categoriaRepository.existsByNomeIgnoreCase("Terror")).thenReturn(false);
        Mockito.when(categoriaRepository.save(categoriaExistente)).thenReturn(categoriaSalva);
        Mockito.when(modelMapper.map(categoriaSalva, CategoriaResponseDTO.class)).thenReturn(dto);

        // --- ACT ---
        CategoriaResponseDTO resultado = categoriaService.update(1L, request);

        // --- ASSERT ---
        assertNotNull(resultado);
        assertEquals("Terror", resultado.getNome());

        Mockito.verify(categoriaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(categoriaRepository, Mockito.times(1)).save(categoriaExistente);
    }

    @Test
    void deveAtualizarCategoriaSemMudarNome() {
        // --- ARRANGE ---
        CategoriaRequestDTO request = new CategoriaRequestDTO("Ficção Científica", "Nova descrição");
        Categoria categoriaExistente = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");
        Categoria categoriaSalva = new Categoria(1L, "Ficção Científica", "Nova descrição");
        CategoriaResponseDTO dto = new CategoriaResponseDTO(1L, "Ficção Científica", "Nova descrição");

        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaExistente));
        Mockito.when(categoriaRepository.save(categoriaExistente)).thenReturn(categoriaSalva);
        Mockito.when(modelMapper.map(categoriaSalva, CategoriaResponseDTO.class)).thenReturn(dto);

        // --- ACT ---
        CategoriaResponseDTO resultado = categoriaService.update(1L, request);

        // --- ASSERT ---
        assertNotNull(resultado);
        assertEquals("Nova descrição", resultado.getDescricao());

        // Nome igual ao atual — existsByNomeIgnoreCase não deve ser chamado
        Mockito.verify(categoriaRepository, Mockito.never()).existsByNomeIgnoreCase(Mockito.any());
        Mockito.verify(categoriaRepository, Mockito.times(1)).save(categoriaExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarComNomeDuplicado() {
        // --- ARRANGE ---
        CategoriaRequestDTO request = new CategoriaRequestDTO("Terror", "Livros de terror");
        Categoria categoriaExistente = new Categoria(1L, "Ficção Científica", "Livros de ficção científica");

        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaExistente));
        Mockito.when(categoriaRepository.existsByNomeIgnoreCase("Terror")).thenReturn(true);

        // --- ACT + ASSERT ---
        assertThrows(AlreadyExistsException.class, () -> categoriaService.update(1L, request));

        Mockito.verify(categoriaRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarCategoriaNaoEncontrada() {
        // --- ARRANGE ---
        CategoriaRequestDTO request = new CategoriaRequestDTO("Terror", "Livros de terror");

        Mockito.when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        // --- ACT + ASSERT ---
        assertThrows(ObjectNotFoundException.class, () -> categoriaService.update(99L, request));

        Mockito.verify(categoriaRepository, Mockito.never()).save(Mockito.any());
    }
}