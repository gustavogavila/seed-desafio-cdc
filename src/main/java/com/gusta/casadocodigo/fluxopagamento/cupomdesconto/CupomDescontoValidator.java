package com.gusta.casadocodigo.fluxopagamento.cupomdesconto;

import com.gusta.casadocodigo.fluxopagamento.parte01.NovaCompraRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

// 6
@Component
public class CupomDescontoValidator implements Validator {

    private final CupomDescontoRepository cupomDescontoRepository;

    @Autowired
    public CupomDescontoValidator(CupomDescontoRepository cupomDescontoRepository) {
        this.cupomDescontoRepository = cupomDescontoRepository;
    }

    // 1
    @Override
    public boolean supports(Class<?> aClass) {
        return NovaCompraRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        // 1
        if (errors.hasErrors()) {
            return;
        }

        NovaCompraRequest novaCompraRequest = (NovaCompraRequest) o;
        Optional<String> possivelCupomInformado = novaCompraRequest.getCodigoCupomDesconto();

        // 1
        if (possivelCupomInformado.isPresent()) {
            // 1
            Optional<CupomDesconto> possivelCupomExistente = cupomDescontoRepository.findByCodigo(possivelCupomInformado.get());
            // 1
            possivelCupomExistente.ifPresent(cupomDesconto -> {
                if(!cupomDesconto.estaValido()) {
                    errors.rejectValue("codigoCupomDesconto", "CupomDescontoInexistente", "Cupom informado inválido");
                }
            });
        }
    }
}
