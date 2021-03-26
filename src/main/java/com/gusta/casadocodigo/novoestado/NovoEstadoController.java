package com.gusta.casadocodigo.novoestado;

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
@RequestMapping(value = "novos-estados")
public class NovoEstadoController {

    @PersistenceContext
    private EntityManager em;

    @PostMapping
    @Transactional
    public ResponseEntity<EstadoResponse> criar(@RequestBody @Valid NovoEstadoRequest request) {
        Estado estado = request.toModel(em);
        em.persist(estado);
        EstadoResponse estadoResponse = new EstadoResponse(estado);
        return ResponseEntity.ok(estadoResponse);
    }

}
