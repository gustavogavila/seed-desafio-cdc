package com.gusta.casadocodigo.detalhelivro;

import com.gusta.casadocodigo.novolivro.Livro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static java.util.Objects.isNull;

// 3
@RestController
@RequestMapping(value = "produtos")
public class DetalheLivroController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("{id}")
    public ResponseEntity<LivroDetalheParaSiteResponse> buscarDetalheLivroPor(@PathVariable("id") Long id) {
        // 1
        Livro livro = entityManager.find(Livro.class, id);

        // 1
        if (isNull(livro)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
        }
        // 1
        return ResponseEntity.ok(new LivroDetalheParaSiteResponse(livro));
    }

}
