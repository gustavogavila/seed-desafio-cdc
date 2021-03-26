package com.gusta.casadocodigo.novopais;

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
@RequestMapping(value = "novos-paises")
public class NovoPaisController {

    @PersistenceContext
    private EntityManager em;

    @PostMapping
    @Transactional
    // 3
    public ResponseEntity<PaisResponse> criar(@RequestBody @Valid NovoPaisRequest request) {
        Pais pais = request.toModel();
        em.persist(pais);
        PaisResponse paisResponse = new PaisResponse(pais);
        return ResponseEntity.ok(paisResponse);
    }
}
