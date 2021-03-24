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

    public Livro(@NotBlank String titulo, @NotBlank @Size(max = 500) String resumo,
                 @NotNull @DecimalMin("20.0") BigDecimal preco, @NotNull @Min(100) Integer numeroDePaginas,
                 @NotBlank String isbn, @Future LocalDateTime dataPublicacao, @NotNull Categoria categoria,
                 @NotNull Autor autor) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.preco = preco;
        this.numeroDePaginas = numeroDePaginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.categoria = categoria;
        this.autor = autor;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }
}
