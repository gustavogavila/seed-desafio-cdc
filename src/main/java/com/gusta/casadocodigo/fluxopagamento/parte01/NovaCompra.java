package com.gusta.casadocodigo.fluxopagamento.parte01;

import com.gusta.casadocodigo.fluxopagamento.cupomdesconto.CupomDesconto;
import com.gusta.casadocodigo.fluxopagamento.parte02.Carrinho;
import com.gusta.casadocodigo.novoestado.Estado;
import com.gusta.casadocodigo.novopais.Pais;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.function.Function;

// 3
@Entity
public class NovaCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // 1
    @ManyToOne
    private Estado estado;

    // 1
    @NotNull
    @ManyToOne
    private Pais pais;

    @NotBlank
    private String telefone;

    @NotBlank
    private String cep;

    @OneToOne(mappedBy = "novaCompra", cascade = CascadeType.PERSIST)
    private Carrinho carrinho;

    private String codigoCupomDesconto;

    // 1
    private NovaCompra(NovaCompraBuilder builder) {
        this.email = builder.email;
        this.nome = builder.nome;
        this.sobrenome = builder.sobrenome;
        this.documento = builder.documento;
        this.endereco = builder.endereco;
        this.complemento = builder.complemento;
        this.cidade = builder.cidade;
        this.pais = builder.pais;
        this.telefone = builder.telefone;
        this.cep = builder.cep;
        this.estado = builder.estado;
        this.carrinho = builder.carrinho;
    }

    public Long getId() {
        return id;
    }

    public void associarCupomDesconto(CupomDesconto cupomDesconto) {
        this.codigoCupomDesconto = cupomDesconto.getCodigo();
    }

    public static class NovaCompraBuilder {

        public String email;
        public String nome;
        public String sobrenome;
        public String documento;
        public String endereco;
        public String complemento;
        public String cidade;
        public Pais pais;
        public String telefone;
        public String cep;
        public Estado estado;
        public Carrinho carrinho;
        public Function<NovaCompra, Carrinho> novaCompraCarrinhoFunction;

        public NovaCompraBuilder(String email, String nome, String sobrenome, String documento, String endereco,
                                 String complemento, String cidade, Pais pais, String telefone, String cep,
                                 Function<NovaCompra, Carrinho> novaCompraCarrinhoFunction) {
            this.email = email;
            this.nome = nome;
            this.sobrenome = sobrenome;
            this.documento = documento;
            this.endereco = endereco;
            this.complemento = complemento;
            this.cidade = cidade;
            this.pais = pais;
            this.telefone = telefone;
            this.cep = cep;
            this.novaCompraCarrinhoFunction = novaCompraCarrinhoFunction;
        }

        public NovaCompraBuilder comEstado(Estado estado) {
            this.estado = estado;
            return this;
        }

        public NovaCompra build() {
            NovaCompra novaCompra = new NovaCompra(this);
            novaCompra.carrinho = this.novaCompraCarrinhoFunction.apply(novaCompra);
            return novaCompra;
        }
    }

    @Override
    public String toString() {
        return "NovaCompra{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", documento='" + documento + '\'' +
                ", endereco='" + endereco + '\'' +
                ", complemento='" + complemento + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado=" + estado +
                ", pais=" + pais +
                ", telefone='" + telefone + '\'' +
                ", cep='" + cep + '\'' +
                ", carrinho=" + carrinho.getItens().size() +
                '}';
    }
}
