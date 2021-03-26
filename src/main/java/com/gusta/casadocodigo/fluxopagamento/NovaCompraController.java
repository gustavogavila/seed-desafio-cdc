package com.gusta.casadocodigo.fluxopagamento;

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
@RequestMapping(value = "novas-compras")
public class NovaCompraController {


    @PersistenceContext
    private EntityManager em;

    @PostMapping
    @Transactional
    public ResponseEntity<String> criar(@RequestBody @Valid NovaCompraRequest request) {

        NovaCompra novaCompra = request.toModel(em);

        return ResponseEntity.ok("Compra realizada com sucesso");
    }
}
