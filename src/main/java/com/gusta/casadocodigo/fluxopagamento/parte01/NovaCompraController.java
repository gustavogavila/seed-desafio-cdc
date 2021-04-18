package com.gusta.casadocodigo.fluxopagamento.parte01;

import com.gusta.casadocodigo.fluxopagamento.cupomdesconto.CupomDescontoRepository;
import com.gusta.casadocodigo.fluxopagamento.cupomdesconto.CupomDescontoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;

// 3
@RestController
@RequestMapping(value = "novas-compras")
public class NovaCompraController {


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CupomDescontoRepository cupomDescontoRepository;

    // 1
    @Autowired
    private EstadoObrigatorioValidator estadoObrigatorioValidator;

    @Autowired
    private CupomDescontoValidator cupomDescontoValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(estadoObrigatorioValidator, cupomDescontoValidator);
    }

    @PostMapping
    @Transactional
    // 1
    public ResponseEntity<String> criar(@RequestBody @Valid NovaCompraRequest request) {

        // 1
        NovaCompra novaCompra = request.toModel(em, cupomDescontoRepository);

        em.persist(novaCompra);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/detalhe-compra/{id}")
                .buildAndExpand(novaCompra.getId()).toUri();

        return ResponseEntity.created(URI.create(uri.toString())).build();
    }
}
