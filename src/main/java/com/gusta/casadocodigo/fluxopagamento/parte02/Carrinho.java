package com.gusta.casadocodigo.fluxopagamento.parte02;

import com.gusta.casadocodigo.fluxopagamento.cupomdesconto.CupomDesconto;
import com.gusta.casadocodigo.fluxopagamento.parte01.NovaCompra;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    private BigDecimal total;

    @NotNull
    @NotEmpty
    @ElementCollection
    private List<Item> itens = new ArrayList<>();

    @NotNull
    @OneToOne
    private NovaCompra novaCompra;

    @Deprecated
    public Carrinho() {
    }

    public Carrinho(@NotNull @Valid NovaCompra novaCompra, @NotNull @NotEmpty List<Item> itens) {
        this.itens = itens;
        this.novaCompra = novaCompra;
        this.total = calcularTotal();
    }

    public List<Item> getItens() {
        return itens;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal calcularTotal() {
        return itens.stream().map(item -> item.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public NovaCompra getNovaCompra() {
        return novaCompra;
    }

    public BigDecimal getTotalComCupomDesconto(CupomDesconto cupomDesconto) {
        Double percentualDesconto = cupomDesconto.getPercentualDesconto();
        return total.subtract((BigDecimal.valueOf(percentualDesconto)
                .divide(BigDecimal.valueOf(100))).multiply(total))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public void aplicarCupomDescontoAoCarrinho(CupomDesconto cupomDesconto) {
        this.total = getTotalComCupomDesconto(cupomDesconto);
    }
}
