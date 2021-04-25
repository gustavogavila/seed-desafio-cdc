package com.gusta.casadocodigo.fluxopagamento.cupomdesconto;

import com.gusta.casadocodigo.fluxopagamento.parte01.NovaCompraRequest;
import com.gusta.casadocodigo.fluxopagamento.parte02.CarrinhoRequest;
import com.gusta.casadocodigo.fluxopagamento.parte02.ItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CupomDescontoValidatorTest {

    private NovaCompraRequest request;
    private CupomDesconto cupomDesconto;
    private CupomDescontoRepository cupomDescontoRepository = mock(CupomDescontoRepository.class);

    @BeforeEach
    public void setup() {
        @NotNull List<ItemRequest> itensRequest = Arrays.asList(new ItemRequest(1L, 5));
        @NotNull CarrinhoRequest carrinho = new CarrinhoRequest(BigDecimal.valueOf(50.0), itensRequest);;
        request = new NovaCompraRequest("teste@email.com", "Cliente", "Teste",
                "117.443.480-57","Rua dos testes, 100", "apto 100", "Fortaleza",
                null, 1L, "88998776655","60000000", carrinho);
        cupomDesconto = new CupomDesconto("CODIGO", BigDecimal.TEN.doubleValue(), LocalDateTime.now().plusDays(1));
    }

    @Test
    void devePassarCasoJaExistaErro() {
        Errors errors = new BeanPropertyBindingResult(request, "target");
        errors.reject("codigo");
        CupomDescontoValidator validator = new CupomDescontoValidator(cupomDescontoRepository);

        validator.validate(request, errors);

        assertTrue(errors.hasErrors());
        assertEquals("codigo", errors.getGlobalErrors().get(0).getCode());
    }

    @Test
    void devePassarCasoNaoSejaInformadoCupom() {
        Errors errors = new BeanPropertyBindingResult(request, "target");
        CupomDescontoValidator validator = new CupomDescontoValidator(cupomDescontoRepository);

        validator.validate(request, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void deveInvalidarCasoHajaCupomForaDaValidade() {
        ReflectionTestUtils.setField(cupomDesconto,"validade", LocalDateTime.now().minusDays(2));
        request.setCodigoCupomDesconto(cupomDesconto.getCodigo());
        when(cupomDescontoRepository.findByCodigo(cupomDesconto.getCodigo())).thenReturn(Optional.of(cupomDesconto));
        Errors errors = new BeanPropertyBindingResult(request, "target");
        CupomDescontoValidator validator = new CupomDescontoValidator(cupomDescontoRepository);

        validator.validate(request, errors);

        assertTrue(errors.hasErrors());
        assertEquals("Cupom informado inválido", errors.getFieldError("codigoCupomDesconto")
                .getDefaultMessage());
    }

    @Test
    void devePassarCasoHajaCupomValido() {
        request.setCodigoCupomDesconto(cupomDesconto.getCodigo());
        when(cupomDescontoRepository.findByCodigo(cupomDesconto.getCodigo())).thenReturn(Optional.of(cupomDesconto));
        Errors errors = new BeanPropertyBindingResult(request, "target");
        CupomDescontoValidator validator = new CupomDescontoValidator(cupomDescontoRepository);

        validator.validate(request, errors);

        assertFalse(errors.hasErrors());
    }
}
