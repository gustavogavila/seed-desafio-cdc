package com.gusta.casadocodigo.novoestado;

import com.gusta.casadocodigo.compartilhado.UniqueValue;
import com.gusta.casadocodigo.novopais.Pais;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static java.util.Objects.nonNull;

public class NovoEstadoRequest {

    @NotBlank
    @UniqueValue(domainClass = Estado.class, fieldName = "nome")
    private String nome;

    @NotNull
    private Long paisId;

    public NovoEstadoRequest(@NotBlank String nome, @NotNull Long paisId) {
        this.nome = nome;
        this.paisId = paisId;
    }

    public Estado toModel(EntityManager em) {
        Pais pais = em.find(Pais.class, paisId);
        Assert.state(nonNull(pais), "O pais informado não existe na base de dados" + paisId);
        return new Estado(nome, pais);
    }
}
