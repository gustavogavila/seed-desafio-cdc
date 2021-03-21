package com.gusta.casadocodigo.novacategoria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gusta.casadocodigo.compartilhado.UniqueValue;

import javax.validation.constraints.NotBlank;

// 1
public class NovaCategoriaRequest {

    @NotBlank
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome")
    private String nome;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovaCategoriaRequest(@NotBlank String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    // 1
    public Categoria toModel() {
        return new Categoria(nome);
    }
}
