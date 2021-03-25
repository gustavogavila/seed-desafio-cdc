package com.gusta.casadocodigo.novolivro;

public class LivroResponse {
    private Long id;
    private String titulo;

    public LivroResponse(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }
}
