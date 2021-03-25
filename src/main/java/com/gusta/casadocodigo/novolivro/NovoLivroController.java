package com.gusta.casadocodigo.novolivro;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// 2
@RestController
@RequestMapping(value = "novos-livros")
public class NovoLivroController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    // 1
    public ResponseEntity<Void> criar(@RequestBody @Valid NovoLivroRequest request) {
        // 1
        Livro livro = request.toModel(entityManager);
        entityManager.persist(livro);
        return ResponseEntity.ok().build();
    }
}
