package com.gusta.casadocodigo.fluxopagamento.parte02;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @NotNull
    @Positive
    private BigDecimal total;

    @NotNull
    @NotEmpty
    private List<Item> itens = new ArrayList<>();

    @Deprecated
    public Carrinho() {
    }

    public Carrinho(@NotNull @Positive BigDecimal total, @NotNull @NotEmpty List<Item> itens) {
        this.total = total;
        this.itens = itens;
    }

    public List<Item> getItens() {
        return itens;
    }
}
