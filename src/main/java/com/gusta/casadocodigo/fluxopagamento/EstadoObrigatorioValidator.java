package com.gusta.casadocodigo.fluxopagamento;

import com.gusta.casadocodigo.novopais.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

import static java.util.Objects.isNull;

// 4
@Component
public class EstadoObrigatorioValidator implements Validator {

    private final EntityManager entityManager;

    @Autowired
    public EstadoObrigatorioValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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

        // 1
        NovaCompraRequest novaCompraRequest = (NovaCompraRequest) o;

        // 1
        Pais pais = entityManager.find(Pais.class, novaCompraRequest.getPaisId());
        Long estadoId = novaCompraRequest.getEstadoId();

        // 1
        if (isNull(estadoId) && pais.temEstados()) {
            errors.reject("EstadoObrigatorioValidator", "O Estado é obrigatório");
        }
    }
}
