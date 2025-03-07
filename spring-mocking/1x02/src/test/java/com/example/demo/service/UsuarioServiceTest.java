package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        Long id = 1L;
        Usuario usuarioEsperado = new Usuario(id, "João Silva", "joao@email.com");
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioEsperado));

        Usuario usuarioRetornado = usuarioService.buscarUsuarioPorId(id);

        assertNotNull(usuarioRetornado);
        assertEquals(usuarioEsperado.getId(), usuarioRetornado.getId());
        assertEquals(usuarioEsperado.getNome(), usuarioRetornado.getNome());
        assertEquals(usuarioEsperado.getEmail(), usuarioRetornado.getEmail());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        Long id = 2L;
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.buscarUsuarioPorId(id));
        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        Usuario usuarioParaSalvar = new Usuario(null, "Maria Souza", "maria@email.com");
        Usuario usuarioSalvo = new Usuario(1L, "Maria Souza", "maria@email.com");
        Mockito.when(usuarioRepository.save(usuarioParaSalvar)).thenReturn(usuarioSalvo);

        Usuario resultado = usuarioService.salvarUsuario(usuarioParaSalvar);

        assertNotNull(resultado);
        assertEquals(usuarioSalvo.getId(), resultado.getId());
        assertEquals(usuarioSalvo.getNome(), resultado.getNome());
        assertEquals(usuarioSalvo.getEmail(), resultado.getEmail());
    }
}
