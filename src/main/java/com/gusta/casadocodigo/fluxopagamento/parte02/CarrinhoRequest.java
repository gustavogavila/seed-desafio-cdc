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

    public BigDecimal getTotal() {
        return total;
    }

    // 2
    public Function<NovaCompra, Carrinho> toModel(EntityManager em, CupomDescontoRepository cupomDescontoRepository,
                                                  String codigoCupomDesconto) {

        List<Item> itens = this.itens.stream().map(item -> item.toModel(em)).collect(Collectors.toList());

        return (novaCompra) -> {
            Carrinho carrinho = new Carrinho(novaCompra, itens);
            BigDecimal valorReal = carrinho.getTotal().setScale(3, RoundingMode.HALF_UP);

            // TODO: Finalizar a aplicação e associação do cupom de desconto à compra.
            if (nonNull(codigoCupomDesconto)) {
                CupomDesconto cupomDesconto = cupomDescontoRepository.findByCodigo(codigoCupomDesconto).orElse(null);
                Assert.state(nonNull(cupomDesconto), "O cupom informado não foi encontrado : " + codigoCupomDesconto);
                valorReal = carrinho.aplicarCupomDesconto(cupomDesconto).setScale(3, RoundingMode.HALF_UP);
            }

            BigDecimal valorInformado = total.setScale(3, RoundingMode.HALF_UP);

            Assert.isTrue(valorReal.compareTo(valorInformado) == 0,
                    "O valor total informado não corresponde ao valor real");
            return carrinho;
        };
    }
}
