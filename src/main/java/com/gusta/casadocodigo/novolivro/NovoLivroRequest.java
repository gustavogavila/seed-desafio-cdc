package com.gusta.casadocodigo.novolivro;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gusta.casadocodigo.compartilhado.UniqueValue;
import com.gusta.casadocodigo.novacategoria.Categoria;
import com.gusta.casadocodigo.novoautor.Autor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class NovoLivroRequest {

    @NotBlank
    @UniqueValue(fieldName = "titulo", domainClass = Livro.class)
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
    @UniqueValue(fieldName = "isbn", domainClass = Livro.class)
    private String isbn;

    @Future
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataPublicacao;

    @NotNull
    private Long categoriaId;

    @NotNull
    private Long autorId;

    public NovoLivroRequest(@NotBlank String titulo, @NotBlank @Size(max = 500) String resumo, String sumario,
                            @NotNull @DecimalMin("20.0") BigDecimal preco, @NotNull @Min(100) Integer numeroDePaginas,
                            @NotBlank String isbn, @Future LocalDateTime dataPublicacao, @NotNull Long categoriaId,
                            @NotNull Long autorId) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.numeroDePaginas = numeroDePaginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.categoriaId = categoriaId;
        this.autorId = autorId;
    }

    public Livro toModel() {
        return new Livro.LivroBuilder(titulo, isbn)
                .comResumo(resumo)
                .comPreco(preco)
                .comNumeroDePaginas(numeroDePaginas)
                .comDataPublicacao(dataPublicacao)
                .comSumario(sumario)
                .comCategoria(Categoria.init().comId(categoriaId))
                .comAutor(Autor.init().comId(autorId))
                .build();
    }
}
