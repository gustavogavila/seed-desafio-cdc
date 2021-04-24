package com.gusta.casadocodigo.fluxopagamento.cupomdesconto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class CupomDescontoTest {

    @ParameterizedTest
    @CsvSource({"0, true", "1, true"})
    void cupomDeveEstarValido(int numeroDias, boolean resultado) {
        CupomDesconto cupomDesconto = new CupomDesconto("CUPOMTESTE", BigDecimal.TEN.doubleValue(),
                LocalDateTime.now().plusDays(numeroDias));
        assertEquals(resultado, cupomDesconto.estaValido());
    }

    @Test
    void cupomDeveEstarInvalido() {
        CupomDesconto cupomDesconto = new CupomDesconto("CUPOMTESTE", BigDecimal.TEN.doubleValue(), LocalDateTime.now().plusDays(1));
        ReflectionTestUtils.setField(cupomDesconto, "validade", LocalDateTime.now().minusDays(1));
        assertFalse(cupomDesconto.estaValido());
    }

    @Test
    void naoDevePermitirCriarCupomDescontoComDataAnteriorAAtual() {
        assertThrows(IllegalArgumentException.class,
                () -> new CupomDesconto("CUPOMTESTE", BigDecimal.TEN.doubleValue(), LocalDateTime.now().minusDays(1)));
    }

}
