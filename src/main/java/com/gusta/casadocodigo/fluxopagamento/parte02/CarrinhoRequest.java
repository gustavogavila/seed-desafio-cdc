package com.gusta.casadocodigo.fluxopagamento.parte02;

import com.gusta.casadocodigo.fluxopagamento.cupomdesconto.CupomDesconto;
import com.gusta.casadocodigo.fluxopagamento.cupomdesconto.CupomDescontoRepository;
import com.gusta.casadocodigo.fluxopagamento.parte01.NovaCompra;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

// 2
public class CarrinhoRequest {

    @NotNull
    @Positive
    private BigDecimal total;

    @NotNull
    @NotEmpty
    private List<ItemRequest> itens = new ArrayList<>();

    public CarrinhoRequest(@NotNull BigDecimal total, @NotNull List<ItemRequest> itens) {
        this.total = total;
        this.itens = itens;
    }

    // 2
    public Function<NovaCompra, Carrinho> toModel(EntityManager em, CupomDescontoRepository cupomDescontoRepository,
                                                  String codigoCupomDesconto) {

        List<Item> itens = this.itens.stream().map(item -> item.toModel(em)).collect(Collectors.toList());

        return (novaCompra) -> {
            Carrinho carrinho = new Carrinho(novaCompra, itens);
            BigDecimal valorReal = carrinho.getTotal();
            BigDecimal valorInformado = total.setScale(3, RoundingMode.HALF_UP);;

            if (nonNull(codigoCupomDesconto) && !codigoCupomDesconto.equals("")) {
                CupomDesconto cupomDesconto = cupomDescontoRepository.findByCodigo(codigoCupomDesconto).orElse(null);
                Assert.state(nonNull(cupomDesconto), "O cupom informado não foi encontrado : " + codigoCupomDesconto);

                valorReal = carrinho.getTotalComCupomDesconto(cupomDesconto).setScale(3, RoundingMode.HALF_UP);
                verificarSeValorInformadoIgualValorReal(valorInformado, valorReal);
                carrinho.aplicarCupomDescontoAoCarrinho(cupomDesconto);
                carrinho.getNovaCompra().associarCupomDesconto(cupomDesconto);
                return carrinho;
            }

            valorInformado = valorInformado.setScale(3, RoundingMode.HALF_UP);
            valorReal = valorReal.setScale(3, RoundingMode.HALF_UP);
            verificarSeValorInformadoIgualValorReal(valorInformado, valorReal);

            return carrinho;
        };
    }

    private void verificarSeValorInformadoIgualValorReal(BigDecimal valorInformado, BigDecimal valorReal) {
        Assert.isTrue(valorReal.compareTo(valorInformado) == 0,
                "O valor total informado não corresponde ao valor real");
    }
}
