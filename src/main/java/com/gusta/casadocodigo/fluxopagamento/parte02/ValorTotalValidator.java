package com.gusta.casadocodigo.fluxopagamento.parte02;

import com.gusta.casadocodigo.fluxopagamento.parte01.NovaCompraRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

// 4
@Component
public class ValorTotalValidator implements Validator {

    private final EntityManager entityManager;

    @Autowired
    public ValorTotalValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
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
        // 1
        CarrinhoRequest carrinhoRequest = novaCompraRequest.getCarrinhoRequest();

        // 1
        Carrinho carrinho = carrinhoRequest.toModel(entityManager);
        BigDecimal valorTotalCalculado = carrinho.getTotal();

        // 1
        if (valorInformadoNaoConfereComValorCalculado(carrinhoRequest, valorTotalCalculado)) {
            errors.rejectValue("carrinhoRequest", "ValorTotalValidator",
                    "Valor total informado não corresponde ao valor total calculado");
        }
    }

    private boolean valorInformadoNaoConfereComValorCalculado(CarrinhoRequest carrinhoRequest,
                                                              BigDecimal valorTotalCalculado) {
        return valorTotalCalculado.compareTo(carrinhoRequest.getTotal()) != 0;
    }
}
