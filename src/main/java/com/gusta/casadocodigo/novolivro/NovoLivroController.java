package com.gusta.casadocodigo.novolivro;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.nonNull;

// 4
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

    @GetMapping
    // 1
    public ResponseEntity<List<LivroResponse>> pegarTodos() {
        List<LivroResponse> resultList = entityManager
                .createQuery("SELECT NEW com.gusta.casadocodigo.novolivro.LivroResponse(l.id, l.titulo) FROM Livro l", LivroResponse.class)
                .getResultList();
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("{id}")
    // 1
    public ResponseEntity<LivroDetalheParaSiteResponse> pegarPorId(@PathVariable Long id) {
        Livro livro = entityManager
                .createQuery("FROM Livro l WHERE id = :id", Livro.class)
                .setParameter("id", id)
                .getResultStream().findFirst().orElse(null);

        LivroDetalheParaSiteResponse response = new LivroDetalheParaSiteResponse(livro);
        return nonNull(livro) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }
}
