package com.gusta.casadocodigo.fluxopagamento;

import com.gusta.casadocodigo.novoestado.Estado;
import com.gusta.casadocodigo.novopais.Pais;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovaCompra {

    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @NotBlank
    @CPF
    private String documento;

    @NotBlank
    private String endereco;

    @NotBlank
    private String complemento;

    @NotBlank
    private String cidade;

    private Estado estado;

    @NotNull
    private Pais pais;

    @NotBlank
    private String telefone;

    @NotBlank
    private String cep;

    public NovaCompra(@NotBlank @Email String email, @NotBlank String nome, @NotBlank String sobrenome,
                      @NotBlank @CPF String documento, @NotBlank String endereco, @NotBlank String complemento,
                      @NotBlank String cidade, Estado estado, @NotNull Pais pais, @NotBlank String telefone,
                      @NotBlank String cep) {
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.documento = documento;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.telefone = telefone;
        this.cep = cep;
    }
}
