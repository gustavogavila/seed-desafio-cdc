package com.gusta.casadocodigo.fluxopagamento.detalhecompra;

import com.gusta.casadocodigo.fluxopagamento.parte01.NovaCompra;
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
@RequestMapping(value = "detalhe-compra")
public class DetalheCompraController {

    @PersistenceContext
    private EntityManager em;

    @GetMapping("{compraId}")
    public ResponseEntity<DetalheCompraResponse> buscarPorId(@PathVariable Long compraId) {
        // 1
        NovaCompra novaCompra = em.find(NovaCompra.class, compraId);
        // 1
        if (isNull(novaCompra)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra não encontrada");
        }
        // 1
        DetalheCompraResponse detalheCompraResponse = new DetalheCompraResponse(novaCompra);
        return ResponseEntity.ok(detalheCompraResponse);
    }
}
