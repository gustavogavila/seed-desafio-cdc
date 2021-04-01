package com.gusta.casadocodigo.fluxopagamento;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
// 2
public class NovaCompraGroupSequenceProvider implements DefaultGroupSequenceProvider<NovaCompraRequest> {

    @Override
    // 1
    public List<Class<?>> getValidationGroups(NovaCompraRequest novaCompraRequest) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(NovaCompraRequest.class);

        // 1
        if (isTipoPessoaSelecionado(novaCompraRequest)) {
            groups.add(novaCompraRequest.getTipoPessoa().getGroup());
        }

        return groups;
    }

    private boolean isTipoPessoaSelecionado(NovaCompraRequest novaCompraRequest) {
        return nonNull(novaCompraRequest) && nonNull(novaCompraRequest.getTipoPessoa());
    }
}
