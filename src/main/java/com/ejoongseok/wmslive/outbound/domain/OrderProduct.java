package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.product.domain.Product;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public class OrderProduct {
    private final Product product;
    private final Long orderQuantity;
    private final Long unitPrice;

    public OrderProduct(final Product product, final Long orderQuantity, final Long unitPrice) {
        this.product = product;
        this.orderQuantity = orderQuantity;
        this.unitPrice = unitPrice;
    }

    public Long getProductNo() {
        return product.getProductNo();
    }

    public Long getWeight() {
        return product.getWeightInGrams() * orderQuantity;
    }

    public Long getVolume() {
        return product.getVolume() * orderQuantity;
    }
}
