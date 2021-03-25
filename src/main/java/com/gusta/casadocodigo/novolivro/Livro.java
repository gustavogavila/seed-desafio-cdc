package com.gusta.casadocodigo.novolivro;

import com.gusta.casadocodigo.novacategoria.Categoria;
import com.gusta.casadocodigo.novoautor.Autor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String resumo;

    private String sumario;

    @NotNull
    @DecimalMin("20.0")
    private BigDecimal preco;

    @NotNull
    @Min(100)
    private Integer numeroDePaginas;

    @NotBlank
    private String isbn;

    @Future
    private LocalDateTime dataPublicacao;

    @NotNull
    @ManyToOne
    private Categoria categoria;

    @NotNull
    @ManyToOne
    private Autor autor;

    @Deprecated
    public Livro() {
    }

    private Livro(LivroBuilder builder) {
        this.titulo = builder.titulo;
        this.isbn = builder.isbn;
        this.resumo = builder.resumo;
        this.preco = builder.preco;
        this.numeroDePaginas = builder.numeroDePaginas;
        this.dataPublicacao = builder.dataPublicacao;
        this.sumario = builder.sumario;
        this.categoria = builder.categoria;
        this.autor = builder.autor;
    }

    public static class LivroBuilder {
        private String titulo;
        private String isbn;

        private String resumo;
        private BigDecimal preco;
        private int numeroDePaginas;
        private LocalDateTime dataPublicacao;
        private String sumario;
        private Categoria categoria;
        private Autor autor;

        public LivroBuilder(String titulo, String isbn) {
            this.titulo = titulo;
            this.isbn = isbn;
        }

        public LivroBuilder comResumo(String resumo) {
            this.resumo = resumo;
            return this;
        }

        public LivroBuilder comPreco(BigDecimal preco) {
            this.preco = preco;
            return this;
        }

        public LivroBuilder comNumeroDePaginas(int numeroDePaginas) {
            this.numeroDePaginas = numeroDePaginas;
            return this;
        }

        public LivroBuilder comDataPublicacao(LocalDateTime dataPublicacao) {
            this.dataPublicacao = dataPublicacao;
            return this;
        }

        public LivroBuilder comSumario(String sumario) {
            this.sumario = sumario;
            return this;
        }

        public LivroBuilder comCategoria(Categoria categoria) {
            this.categoria = categoria;
            return this;
        }

        public LivroBuilder comAutor(Autor autor) {
            this.autor = autor;
            return this;
        }

        public Livro build() {
            return new Livro(this);
        }
    }
}
