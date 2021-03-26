package com.gusta.casadocodigo.fluxopagamento;

import com.gusta.casadocodigo.novoestado.Estado;
import com.gusta.casadocodigo.novopais.Pais;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Objects.nonNull;

public class NovaCompraRequest {

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

    private Long estadoId;

    @NotNull
    private Long paisId;

    @NotBlank
    private String telefone;

    @NotBlank
    private String cep;

    public NovaCompraRequest(@NotBlank @Email String email, @NotBlank String nome, @NotBlank String sobrenome, 
                             @NotBlank @CPF String documento, @NotBlank String endereco, @NotBlank String complemento, 
                             @NotBlank String cidade, Long estadoId, @NotNull Long paisId, @NotBlank String telefone, 
                             @NotBlank String cep) {
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.documento = documento;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estadoId = estadoId;
        this.paisId = paisId;
        this.telefone = telefone;
        this.cep = cep;
    }

    public NovaCompra toModel(EntityManager em) {
        Pais pais = em.find(Pais.class, paisId);
        Estado estado = em.find(Estado.class, estadoId);

        List estados = em.createQuery("SELECT e FROM Estado e where e.pais = :pais")
                .setParameter("pais", pais)
                .getResultList();

        Assert.state(nonNull(pais), "O pais informado não existe: " + paisId);
        Assert.state(nonNull(estados) && estados.size() > 0 && nonNull(estado),
                "O estado informado não existe: " + paisId);

        NovaCompra novaCompra = new NovaCompra(email, nome, sobrenome, documento, endereco, complemento, cidade,
                estado, pais, telefone, cep);

        return novaCompra;
    }
}
