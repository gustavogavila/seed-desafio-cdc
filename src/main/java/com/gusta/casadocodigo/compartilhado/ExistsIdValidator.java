package com.gusta.casadocodigo.compartilhado;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Object> {
    private Class<?> aClass;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(ExistsId params) {
        aClass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = entityManager.createQuery("select 1 from " + aClass.getName() + " where id =:value ");
        query.setParameter("value", value);
        Object singleResult = query.getResultStream().findFirst().orElse(null);
        return nonNull(singleResult);
    }
}
