package com.gusta.casadocodigo.fluxopagamento.cupomdesconto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
public class CupomDesconto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String codigo;

    @NotNull
    @Positive
    private Double percentualDesconto;

    @NotNull
    @Future
    private LocalDateTime validade;

    @Deprecated
    public CupomDesconto() {
    }

    public CupomDesconto(@NotBlank String codigo, @NotNull @Positive Double percentualDesconto,
                         @NotNull @Future LocalDateTime validade) {
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.validade = validade;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDateTime getValidade() {
        return validade;
    }

    public Double getPercentualDesconto() {
        return percentualDesconto;
    }

    public boolean estaValido() {
        return getValidade().compareTo(LocalDateTime.now()) >= 0;
    }

    public boolean confereCom(String cupomInformado) {
        return getCodigo().equals(cupomInformado);
    }
}
