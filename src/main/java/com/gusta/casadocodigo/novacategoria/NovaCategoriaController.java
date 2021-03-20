package com.gusta.casadocodigo.novacategoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "novas-categorias")
public class NovaCategoriaController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NomeUnicoCategoriaValidator nomeUnicoCategoriaValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(nomeUnicoCategoriaValidator);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> criar(@RequestBody @Valid NovaCategoriaRequest request) {
        entityManager.persist(request.toModel());
        return ResponseEntity.ok().build();
    }
}
