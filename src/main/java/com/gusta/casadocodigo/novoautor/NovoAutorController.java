package com.gusta.casadocodigo.novoautor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "novos-autores")
public class NovoAutorController {

    @PersistenceContext
    private EntityManager entityManager;

    // 1 acoplamento contextual NovoAutorRequest
    @PostMapping
    @Transactional
    public ResponseEntity<Void> criar(@RequestBody @Valid NovoAutorRequest request) {
        // 1 acoplamento contextual Autor
        entityManager.persist(request.toModel());
        return ResponseEntity.ok().build();
    }
}
