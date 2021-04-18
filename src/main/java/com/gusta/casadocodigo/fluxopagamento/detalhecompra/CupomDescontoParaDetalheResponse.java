package com.gusta.casadocodigo.fluxopagamento.detalhecompra;

import com.gusta.casadocodigo.fluxopagamento.cupomdesconto.CupomDescontoAplicado;
import com.gusta.casadocodigo.fluxopagamento.parte01.NovaCompra;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Objects.nonNull;

public class CupomDescontoParaDetalheResponse {
    private String codigoCupom;
    private BigDecimal valorComCupomAplicado;

    public CupomDescontoParaDetalheResponse(String codigoCupom, BigDecimal valorComCupomAplicado) {
        this.codigoCupom = codigoCupom;
        this.valorComCupomAplicado = valorComCupomAplicado;
    }

    public CupomDescontoParaDetalheResponse(NovaCompra novaCompra, Optional<CupomDescontoAplicado> possivelCupom) {
        Assert.isTrue(possivelCupom.isPresent(), "Opa! O cupom informado deveria existir nesse ponto!");
        Assert.state(nonNull(novaCompra), "Opa! A compra deveria existir nesse ponto!");
        this.codigoCupom = possivelCupom.get().getCupomDesconto().getCodigo();
        this.valorComCupomAplicado = novaCompra.getValorFinal();
    }

    public String getCodigoCupom() {
        return codigoCupom;
    }

    public BigDecimal getValorComCupomAplicado() {
        return valorComCupomAplicado;
    }
}
