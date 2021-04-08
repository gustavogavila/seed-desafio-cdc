package com.gusta.casadocodigo.fluxopagamento.parte02;

import com.gusta.casadocodigo.compartilhado.ExistsId;
import com.gusta.casadocodigo.novolivro.Livro;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static java.util.Objects.nonNull;

// 1
public class ItemRequest {

    @NotNull
    @ExistsId(domainClass = Livro.class)
    private Long livroId;

    @NotNull
    @Positive
    private Integer quantidade;

    public ItemRequest(@NotNull Long livroId, @Positive Integer quantidade) {
        this.livroId = livroId;
        this.quantidade = quantidade;
    }

    // 1
    public Item toModel(EntityManager em) {
        Livro livro = em.find(Livro.class, livroId);
        Assert.state(nonNull(livro), "Nenhum livro encontrado com o id: " + livroId);
        return new Item(livro, quantidade);
    }
}
