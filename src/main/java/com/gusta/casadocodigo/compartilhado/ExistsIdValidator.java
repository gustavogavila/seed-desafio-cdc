package com.gusta.casadocodigo.compartilhado;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Object> {
    private Class<?> aClass;

    @PersistenceContext
    private EntityManager entityManager;
    private String domainAttribute;

    @Override
    public void initialize(ExistsId params) {
        aClass = params.domainClass();
        domainAttribute = params.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (isNull(value)) {
            return true;
        }

        Optional<Object> possivelStringValue = tratarStringValue(value);
        if (!possivelStringValue.isPresent()) return true;

        value = possivelStringValue.get();

        Query query = null;
        query = entityManager.createQuery("select 1 from " + aClass.getName() + " where "
                + domainAttribute + "=:value ");
        query.setParameter("value", value);
        Object singleResult = query.getResultStream().findFirst().orElse(null);
        return nonNull(singleResult);
    }

    private Optional<Object> tratarStringValue(Object value) {
        if (!(value instanceof String)) {
            return Optional.empty();
        }
        String stringValue = ((String) value).trim();
        if (!hasText(stringValue)) {
            return Optional.empty();
        }
        value = stringValue;
        return Optional.ofNullable(value);
    }
}
