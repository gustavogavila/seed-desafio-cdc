package com.gusta.casadocodigo.fluxopagamento.cupomdesconto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CupomDescontoRepository extends JpaRepository<CupomDesconto, Long> {
    Optional<CupomDesconto> findByCodigo(String codigo);
}
