package com.gusta.casadocodigo.fluxopagamento.parte02;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 2
public class CarrinhoRequest {

    @NotNull
    @Positive
    private BigDecimal total;

    @NotNull
    @NotEmpty
    private List<ItemRequest> itens = new ArrayList<>();

    public CarrinhoRequest(@NotNull BigDecimal total, @NotNull List<ItemRequest> itens) {
        this.total = total;
        this.itens = itens;
    }

    public BigDecimal getTotal() {
        return total;
    }

    // 2
    public Carrinho toModel(EntityManager em) {
        List<Item> itens = this.itens.stream().map(item -> item.toModel(em)).collect(Collectors.toList());
        return new Carrinho(itens);
    }
}
