package com.gusta.casadocodigo.fluxopagamento.detalhecompra;

import com.gusta.casadocodigo.fluxopagamento.cupomdesconto.CupomDescontoAplicado;
import com.gusta.casadocodigo.fluxopagamento.parte01.NovaCompra;
import org.springframework.util.Assert;

import java.util.Optional;

import static java.util.Objects.nonNull;

public class DetalheCompraResponse {
    private Boolean existeCupomAssociado;
    private CupomDescontoParaDetalheResponse cupomDesconto;

    // 1
    public DetalheCompraResponse(NovaCompra novaCompra) {
        Assert.state(nonNull(novaCompra), "Compra não encontrada com id: " + novaCompra.getId());

        // 1 CupomDescontoAplicado.class
        Optional<CupomDescontoAplicado> possivelCupom = novaCompra.getCupomDescontoAplicado();

        this.existeCupomAssociado = possivelCupom.isPresent();

        // 1 CupomDescontoParaDetalheResponse.class
        // 1 ternário
        this.cupomDesconto = possivelCupom.isPresent()
                ? new CupomDescontoParaDetalheResponse(novaCompra, possivelCupom)
                : null;
    }

    public Boolean getExisteCupomAssociado() {
        return existeCupomAssociado;
    }

    public CupomDescontoParaDetalheResponse getCupomDesconto() {
        return cupomDesconto;
    }
}
