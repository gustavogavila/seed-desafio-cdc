package com.gusta.casadocodigo.fluxopagamento.parte02;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class Carrinho {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    private BigDecimal total;

    @NotNull
    @NotEmpty
    private List<Item> itens = new ArrayList<>();

    @Deprecated
    public Carrinho() {
    }

    public Carrinho(@NotNull @NotEmpty List<Item> itens) {
        this.itens = itens;
        this.total = getTotal();
    }

    public List<Item> getItens() {
        return itens;
    }

    public BigDecimal getTotal() {
        return itens.stream().map(item -> item.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
