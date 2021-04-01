package com.gusta.casadocodigo.fluxopagamento;

import com.gusta.casadocodigo.compartilhado.ExistsId;
import com.gusta.casadocodigo.novoestado.Estado;
import com.gusta.casadocodigo.novopais.Pais;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static java.util.Objects.nonNull;

// 6
// 1
@GroupSequenceProvider(NovaCompraGroupSequenceProvider.class)
public class NovaCompraRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @NotNull
    private TipoPessoa tipoPessoa;

    @NotBlank
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
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
        // 1
        Pais pais = em.find(Pais.class, paisId);

        Assert.state(nonNull(pais), "O pais informado não existe: " + paisId);

        // 2
        NovaCompra.NovaCompraBuilder novaCompraBuilder = new NovaCompra.NovaCompraBuilder(email, nome, sobrenome, documento, endereco,
                complemento, cidade, pais, telefone, cep);

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

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }
}
