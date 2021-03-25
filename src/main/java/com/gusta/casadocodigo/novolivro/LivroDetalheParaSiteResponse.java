package com.gusta.casadocodigo.novolivro;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LivroDetalheParaSiteResponse {
    private Long id;
    private String titulo;
    private String resumo;
    private String sumario;
    private BigDecimal preco;
    private Integer numeroDePaginas;
    private String isbn;
    private LocalDateTime dataPublicacao;
    private Long categoriaId;
    private String categoriaNome;
    private Long autorId;
    private String autorNome;
    private String autorDescricao;

    public LivroDetalheParaSiteResponse(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.resumo = livro.getResumo();
        this.sumario = livro.getSumario();
        this.preco = livro.getPreco();
        this.numeroDePaginas = livro.getNumeroDePaginas();
        this.isbn = livro.getIsbn();
        this.dataPublicacao = livro.getDataPublicacao();
        this.categoriaId = livro.getCategoria().getId();
        this.categoriaNome = livro.getCategoria().getNome();
        this.autorId = livro.getAutor().getId();
        this.autorNome = livro.getAutor().getNome();
        this.autorDescricao = livro.getAutor().getDescricao();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public String getSumario() {
        return sumario;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getNumeroDePaginas() {
        return numeroDePaginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public Long getAutorId() {
        return autorId;
    }

    public String getAutorNome() {
        return autorNome;
    }

    public String getAutorDescricao() {
        return autorDescricao;
    }
}
