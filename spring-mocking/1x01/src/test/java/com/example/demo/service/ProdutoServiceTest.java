package com.example.demo.service;


import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {


    @Mock
    private ProdutoRepository produtoRepository;


    @InjectMocks
    private ProdutoService produtoService;


    @Test
    void deveRetornarProdutoQuandoIdExistir() {
      // implemente
       Long id = 1L;
        Produto produtoEsperado = new Produto(id, "Produto Teste", 100.0);
        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoEsperado));

        // Act
        Produto produtoRetornado = produtoService.buscarPorId(id);

        // Assert
        assertNotNull(produtoRetornado);
        assertEquals(produtoEsperado.getId(), produtoRetornado.getId());
        assertEquals(produtoEsperado.getNome(), produtoRetornado.getNome());
        assertEquals(produtoEsperado.getPreco(), produtoRetornado.getPreco());
    }


    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
      // implemente
            // Arrange
        Long id = 2L;
        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> produtoService.buscarPorId(id));
        assertEquals("Produto não encontrado", exception.getMessage());
    }
}