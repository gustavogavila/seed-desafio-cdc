package com.gusta.casadocodigo.fluxopagamento.cupomdesconto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "cupons-desconto")
public class CupomDescontoController {

    @PersistenceContext
    private EntityManager em;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> criar(@Valid @RequestBody CupomDescontoRequest request) {
        em.persist(request.toModel());
        return ResponseEntity.ok().build();
    }
}
