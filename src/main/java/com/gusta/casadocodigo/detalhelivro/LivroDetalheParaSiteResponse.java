package com.gusta.casadocodigo.detalhelivro;

import com.gusta.casadocodigo.novolivro.Livro;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class LivroDetalheParaSiteResponse {
    private Long id;
    private String titulo;
    private String resumo;
    private String sumario;
    private BigDecimal preco;
    private Integer numeroDePaginas;
    private String isbn;
    private String dataPublicacao;
    private AutorDetalheParaSiteResponse autor;

    public LivroDetalheParaSiteResponse(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.resumo = livro.getResumo();
        this.sumario = livro.getSumario();
        this.preco = livro.getPreco();
        this.numeroDePaginas = livro.getNumeroDePaginas();
        this.isbn = livro.getIsbn();
        this.dataPublicacao = livro.getDataPublicacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.autor = new AutorDetalheParaSiteResponse(livro.getAutor());
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

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public AutorDetalheParaSiteResponse getAutor() {
        return autor;
    }
}
