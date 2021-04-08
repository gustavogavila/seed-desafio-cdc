package com.gusta.casadocodigo.fluxopagamento.parte02;

import com.gusta.casadocodigo.novolivro.Livro;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

//@Entity
public class Item {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
//    @ManyToOne
    private Livro livro;

    @NotNull
    @Positive
    private Integer quantidade;

    @Deprecated
    public Item() {
    }

    public Item(@NotNull Livro livro, @NotNull @Positive Integer quantidade) {
        this.livro = livro;
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return livro.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }
}
