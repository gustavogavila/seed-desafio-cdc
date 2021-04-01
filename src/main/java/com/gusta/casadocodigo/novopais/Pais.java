package com.gusta.casadocodigo.novopais;

import com.gusta.casadocodigo.novoestado.Estado;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

// 1
@Entity
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    // 1
    @OneToMany(mappedBy = "pais")
    private List<Estado> estados;

    @Deprecated
    public Pais() {
    }

    public Pais(@NotBlank String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pais pais = (Pais) o;
        return nome.equals(pais.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    public boolean temEstados() {
        return estados.size() > 0;
    }


}
