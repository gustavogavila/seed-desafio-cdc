package com.gusta.casadocodigo.fluxopagamento;

public enum TipoPessoa {
    FISICA("CPF", CpfGroup.class),
    JURIDICA("CNPJ", CnpjGroup.class);

    private final String documento;
    private final Class<?> group;

    private TipoPessoa(String documento, Class<?> group) {
        this.documento = documento;
        this.group = group;
    }

    public String getDocumento() {
        return documento;
    }

    public Class<?> getGroup() {
        return group;
    }
}
