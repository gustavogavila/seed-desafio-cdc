package com.gusta.casadocodigo.novoestado;

import com.gusta.casadocodigo.novopais.Pais;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @ManyToOne
    private Pais pais;

    public Estado(@NotBlank String nome, Pais pais) {
        this.nome = nome;
        this.pais = pais;
    }

    @Deprecated
    public Estado() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Pais getPais() {
        return pais;
    }
}
