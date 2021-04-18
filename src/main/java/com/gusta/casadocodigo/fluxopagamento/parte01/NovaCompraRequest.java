package com.gusta.casadocodigo.fluxopagamento.parte01;

import com.gusta.casadocodigo.compartilhado.Documento;
import com.gusta.casadocodigo.compartilhado.ExistsId;
import com.gusta.casadocodigo.fluxopagamento.cupomdesconto.CupomDesconto;
import com.gusta.casadocodigo.fluxopagamento.cupomdesconto.CupomDescontoRepository;
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
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

// 9
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

    @ExistsId(domainClass = Estado.class, fieldName = "id")
    private Long estadoId;

    @NotNull
    @ExistsId(domainClass = Pais.class, fieldName = "id")
    private Long paisId;

    @NotBlank
    private String telefone;

    @NotBlank
    private String cep;

    // 1
    @NotNull
    private CarrinhoRequest carrinho;

    @ExistsId(domainClass = CupomDesconto.class, fieldName = "codigo")
    private String codigoCupomDesconto;

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

    public NovaCompra toModel(EntityManager em, CupomDescontoRepository cupomDescontoRepository) {
        // 1
        Pais pais = em.find(Pais.class, paisId);

        Assert.state(nonNull(pais), "O pais informado não existe: " + paisId);

        // 1
        // 1
        Function<NovaCompra, Carrinho> novaCompraCarrinhoFunction = this.carrinho.toModel(em);

        // 1
        // 1
        // 1 função como argumento
        NovaCompra.NovaCompraBuilder novaCompraBuilder = new NovaCompra.NovaCompraBuilder(email, nome, sobrenome, documento, endereco,
                complemento, cidade, pais, telefone, cep, novaCompraCarrinhoFunction);

        // 1
        // 1
        if (nonNull(estadoId)) {
            Estado estado = em.find(Estado.class, estadoId);
            Assert.state(nonNull(estado), "O estado informado não existe: " + estadoId);
            novaCompraBuilder.comEstado(estado);
        }

        NovaCompra novaCompra = novaCompraBuilder.build();

        if (nonNull(codigoCupomDesconto) && hasText(codigoCupomDesconto)) {
            Optional<CupomDesconto> possivelCupom = cupomDescontoRepository.findByCodigo(codigoCupomDesconto);
            possivelCupom.ifPresent(cupom -> novaCompra.associarCupomDesconto(cupom));
        }

        return novaCompra;
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

    public Optional<String> getCodigoCupomDesconto() {
        return Optional.ofNullable(codigoCupomDesconto);
    }
}
