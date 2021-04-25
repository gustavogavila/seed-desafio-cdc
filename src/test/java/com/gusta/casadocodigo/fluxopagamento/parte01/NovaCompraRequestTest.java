package com.gusta.casadocodigo.fluxopagamento.parte01;

import com.gusta.casadocodigo.fluxopagamento.cupomdesconto.CupomDescontoRepository;
import com.gusta.casadocodigo.fluxopagamento.parte02.CarrinhoRequest;
import com.gusta.casadocodigo.fluxopagamento.parte02.ItemRequest;
import com.gusta.casadocodigo.novoestado.Estado;
import com.gusta.casadocodigo.novolivro.Livro;
import com.gusta.casadocodigo.novopais.Pais;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class NovaCompraRequestTest {

    private final EntityManager entityManager = Mockito.mock(EntityManager.class);
    private final CupomDescontoRepository cupomDescontoRepository = Mockito.mock(CupomDescontoRepository.class);
    private final List<ItemRequest> itensRequest = Arrays.asList(new ItemRequest(1L, 5));
    private final CarrinhoRequest carrinho = new CarrinhoRequest(BigDecimal.valueOf(50.0), itensRequest);
    private final Pais pais = new Pais("Brasil");
    private final NovaCompraRequest request = new NovaCompraRequest("teste@email.com", "Cliente", "Teste",
            "117.443.480-57","Rua dos testes, 100", "apto 100", "Fortaleza",
            null, 1L, "88998776655","60000000", carrinho);
    private final Livro livro = new Livro.LivroBuilder("LIVRO", "8573934255").comPreco(BigDecimal.TEN).build();

    private final Estado estado = new Estado("Ceará", pais);

    {
        Mockito.when(entityManager.find(Pais.class, 1L)).thenReturn(pais);
        Mockito.when(entityManager.find(Livro.class, 1L)).thenReturn(livro);
        Mockito.when(entityManager.find(Estado.class, 1L)).thenReturn(estado);
    }

    @Test
    void deveCriarCompraComEstadoECupomDesconto() {
        request.setCodigoCupomDesconto("CODIGOCUPOM");
        request.setEstadoId(1L);

        NovaCompra novaCompra = request.toModel(entityManager, cupomDescontoRepository);

        Assertions.assertNotNull(novaCompra);
        Mockito.verify(entityManager).find(Estado.class, 1L);
        Mockito.verify(cupomDescontoRepository).findByCodigo("CODIGOCUPOM");
    }

    @Test
    void deveCriarCompraComEstadoESemCupomDesconto() {
        request.setEstadoId(1L);

        NovaCompra novaCompra = request.toModel(entityManager, cupomDescontoRepository);

        Assertions.assertNotNull(novaCompra);
        Mockito.verify(entityManager).find(Estado.class, 1L);
        Mockito.verify(cupomDescontoRepository, Mockito.never()).findByCodigo(Mockito.anyString());
    }

    @Test
    void deveCriarCompraSemEstadoEComCupom() {
        request.setCodigoCupomDesconto("CODIGOCUPOM");

        NovaCompra novaCompra = request.toModel(entityManager, cupomDescontoRepository);

        Assertions.assertNotNull(novaCompra);
        Mockito.verify(cupomDescontoRepository).findByCodigo("CODIGOCUPOM");
        Mockito.verify(entityManager, Mockito.never()).find(Estado.class, 1L);
    }

    @Test
    void deveCriarCompraSemEstadoESemCupom() {
        NovaCompra novaCompra = request.toModel(entityManager, cupomDescontoRepository);

        Assertions.assertNotNull(novaCompra);
        Mockito.verify(entityManager, Mockito.never()).find(Estado.class, 1L);
        Mockito.verify(cupomDescontoRepository, Mockito.never()).findByCodigo(Mockito.anyString());
    }

}
