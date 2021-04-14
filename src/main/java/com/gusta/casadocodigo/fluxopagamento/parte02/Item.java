package com.gusta.casadocodigo.fluxopagamento.parte02;

import com.gusta.casadocodigo.novolivro.Livro;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Embeddable
public class Item {

    @NotNull
    @ManyToOne
    private Livro livro;

    @NotNull
    @Positive
    private Integer quantidade;

    @Positive
    private BigDecimal precoMomento;

    @Deprecated
    public Item() {
    }

    public Item(@NotNull Livro livro, @NotNull @Positive Integer quantidade) {
        this.livro = livro;
        this.quantidade = quantidade;
        this.precoMomento = livro.getPreco();
    }

    public BigDecimal getValor() {
        return precoMomento.multiply(BigDecimal.valueOf(quantidade));
    }
}
