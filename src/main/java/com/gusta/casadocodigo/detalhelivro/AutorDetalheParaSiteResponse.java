package com.gusta.casadocodigo.detalhelivro;

import com.gusta.casadocodigo.novoautor.Autor;

public class AutorDetalheParaSiteResponse {

    private Long id;
    private String nome;
    private String descricao;

    public AutorDetalheParaSiteResponse(Autor autor) {
        this.id = autor.getId();
        this.nome = autor.getNome();
        this.descricao = autor.getDescricao();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
