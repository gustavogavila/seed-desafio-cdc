package com.gusta.casadocodigo.fluxopagamento.cupomdesconto;

import com.gusta.casadocodigo.fluxopagamento.parte01.NovaCompraRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Component
public class CupomDescontoValidator implements Validator {

    private final CupomDescontoRepository cupomDescontoRepository;

    @Autowired
    public CupomDescontoValidator(CupomDescontoRepository cupomDescontoRepository) {
        this.cupomDescontoRepository = cupomDescontoRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return NovaCompraRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        NovaCompraRequest novaCompraRequest = (NovaCompraRequest) o;

        String codigoCupomDesconto = novaCompraRequest.getCodigoCupomDesconto();

        if (isNull(codigoCupomDesconto) || codigoCupomDesconto.equals("")) {
            return;
        }

        CupomDesconto cupomExistente = cupomDescontoRepository.findByCodigo(codigoCupomDesconto).orElse(null);

        if (isNull(cupomExistente) || !cupomExistente.getCodigo().equals(codigoCupomDesconto)) {
            errors.rejectValue("codigoCupomDesconto", "CupomDescontoInexistente", "Cupom informado não existe");
        }

        if (nonNull(cupomExistente) && cupomExistente.getValidade().isBefore(LocalDateTime.now())) {
            errors.rejectValue("codigoCupomDesconto", "CupomDescontoInvalido", "Cupom vencido");
        }
    }
}
