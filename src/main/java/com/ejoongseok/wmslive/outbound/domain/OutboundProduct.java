package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.product.domain.Product;
import org.springframework.util.Assert;

public class OutboundProduct {
    private final Product product;
    private final Long orderQuantity;
    private final Long unitPrice;

    public OutboundProduct(final Product product, final Long orderQuantity, final Long unitPrice) {
        Assert.notNull(product, "상품은 필수입니다.");
        Assert.notNull(orderQuantity, "주문수량은 필수입니다.");
        if (1 > orderQuantity) throw new IllegalArgumentException("주문수량은 1개 이상이어야 합니다.");
        Assert.notNull(unitPrice, "단가는 필수입니다.");
        if (1 > unitPrice) throw new IllegalArgumentException("단가는 1원 이상이어야 합니다.");
        this.product = product;
        this.orderQuantity = orderQuantity;
        this.unitPrice = unitPrice;
    }
}
