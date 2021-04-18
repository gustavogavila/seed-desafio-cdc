package com.gusta.casadocodigo.fluxopagamento.cupomdesconto;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Embeddable
public class CupomDescontoAplicado {

    @ManyToOne
    private CupomDesconto cupomDesconto;

    @NotNull
    @Positive
    private Double percentualDescontoMomento;

    @NotNull
    @Future
    private LocalDateTime validadeMomento;

    @Deprecated
    public CupomDescontoAplicado() {
    }

    public CupomDescontoAplicado(CupomDesconto cupomDesconto) {
        this.cupomDesconto = cupomDesconto;
        this.percentualDescontoMomento = cupomDesconto.getPercentualDesconto();
        this.validadeMomento = cupomDesconto.getValidade();
    }
}
