package com.gusta.casadocodigo.novopais;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gusta.casadocodigo.compartilhado.UniqueValue;

import javax.validation.constraints.NotBlank;

public class NovoPaisRequest {

    @NotBlank
    @UniqueValue(domainClass = Pais.class, fieldName = "nome")
    private String nome;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovoPaisRequest(@NotBlank String nome) {
        this.nome = nome;
    }

    public Pais toModel() {
        return new Pais(nome);
    }
}
