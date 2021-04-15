package com.gusta.casadocodigo.fluxopagamento.cupomdesconto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gusta.casadocodigo.compartilhado.UniqueValue;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class CupomDescontoRequest {

    @NotBlank
    @UniqueValue(domainClass = CupomDesconto.class, fieldName = "codigo")
    private String codigo;

    @NotNull
    @Positive
    private Double percentualDesconto;

    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validade;

    public CupomDescontoRequest(@NotBlank String codigo, @NotNull @Positive Double percentualDesconto,
                                @NotNull @Future LocalDateTime validade) {
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "CupomDescontoRequest{" +
                "codigo='" + codigo + '\'' +
                ", percentualDesconto=" + percentualDesconto +
                ", validade=" + validade +
                '}';
    }

    public CupomDesconto toModel() {
        return new CupomDesconto(codigo, percentualDesconto, validade);
    }

    public String getCodigo() {
        return codigo;
    }
}
