package com.gusta.casadocodigo.novolivro;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gusta.casadocodigo.compartilhado.ExistsId;
import com.gusta.casadocodigo.compartilhado.UniqueValue;
import com.gusta.casadocodigo.novacategoria.Categoria;
import com.gusta.casadocodigo.novoautor.Autor;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

// 4
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
    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private Long categoriaId;

    @NotNull
    @ExistsId(domainClass = Autor.class, fieldName = "id")
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

    public Livro toModel(EntityManager entityManager) {
        // 1
        @NotNull Categoria categoria = entityManager.find(Categoria.class, categoriaId);

        // 1
        @NotNull Autor autor = entityManager.find(Autor.class, autorId);

        Assert.state(nonNull(categoria), "A categoria de id = " + categoriaId + " não existe na base de dados");
        Assert.state(nonNull(autor), "O autor de id = " + autorId + " não existe na base de dados");

        // 1
        // 1
        return new Livro.LivroBuilder(titulo, isbn)
                .comResumo(resumo)
                .comPreco(preco)
                .comNumeroDePaginas(numeroDePaginas)
                .comDataPublicacao(dataPublicacao)
                .comSumario(sumario)
                .comCategoria(categoria)
                .comAutor(autor)
                .build();
    }
}
