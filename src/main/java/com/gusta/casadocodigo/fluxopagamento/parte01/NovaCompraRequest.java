package com.gusta.casadocodigo.fluxopagamento.parte01;

import com.gusta.casadocodigo.compartilhado.Documento;
import com.gusta.casadocodigo.compartilhado.ExistsId;
import com.gusta.casadocodigo.fluxopagamento.parte02.Carrinho;
import com.gusta.casadocodigo.fluxopagamento.parte02.CarrinhoRequest;
import com.gusta.casadocodigo.novoestado.Estado;
import com.gusta.casadocodigo.novopais.Pais;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.function.Function;

import static java.util.Objects.nonNull;

// 5
public class NovaCompraRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @NotBlank
    @Documento
    private String documento;

    @NotBlank
    private String endereco;

    @NotBlank
    private String complemento;

    @NotBlank
    private String cidade;

    @ExistsId(domainClass = Estado.class)
    private Long estadoId;

    @NotNull
    @ExistsId(domainClass = Pais.class)
    private Long paisId;

    @NotBlank
    private String telefone;

    @NotBlank
    private String cep;

    @NotNull
    private CarrinhoRequest carrinho;

    public NovaCompraRequest(@NotBlank @Email String email, @NotBlank String nome, @NotBlank String sobrenome, 
                             @NotBlank @CPF String documento, @NotBlank String endereco, @NotBlank String complemento, 
                             @NotBlank String cidade, Long estadoId, @NotNull Long paisId, @NotBlank String telefone, 
                             @NotBlank String cep, @NotNull CarrinhoRequest carrinho) {
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
        this.carrinho = carrinho;
    }

    public NovaCompra toModel(EntityManager em) {
        // 1
        Pais pais = em.find(Pais.class, paisId);

        Assert.state(nonNull(pais), "O pais informado não existe: " + paisId);

        Function<NovaCompra, Carrinho> novaCompraCarrinhoFunction = this.carrinho.toModel(em);

        // 2
        NovaCompra.NovaCompraBuilder novaCompraBuilder = new NovaCompra.NovaCompraBuilder(email, nome, sobrenome, documento, endereco,
                complemento, cidade, pais, telefone, cep, novaCompraCarrinhoFunction);

        // 2
        if (nonNull(estadoId)) {
            Estado estado = em.find(Estado.class, estadoId);
            Assert.state(nonNull(estado), "O estado informado não existe: " + estadoId);
            novaCompraBuilder.comEstado(estado);
        }

        return novaCompraBuilder.build();
    }

    public Long getPaisId() {
        return paisId;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public CarrinhoRequest getCarrinhoRequest() {
        return carrinho;
    }
}
