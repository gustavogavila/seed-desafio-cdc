package com.gusta.casadocodigo.fluxopagamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// 3
@RestController
@RequestMapping(value = "novas-compras")
public class NovaCompraController {


    @PersistenceContext
    private EntityManager em;

    // 1
    @Autowired
    private EstadoObrigatorioValidator estadoObrigatorioValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(estadoObrigatorioValidator);
    }

    @PostMapping
    @Transactional
    // 1
    public ResponseEntity<String> criar(@RequestBody @Valid NovaCompraRequest request) {

        // 1
        NovaCompra novaCompra = request.toModel(em);

        return ResponseEntity.ok("Compra realizada com sucesso");
    }
}
