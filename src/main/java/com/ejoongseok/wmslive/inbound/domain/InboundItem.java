package com.ejoongseok.wmslive.inbound.domain;

import com.ejoongseok.wmslive.product.domain.Product;
import org.springframework.util.Assert;

public class InboundItem {
    private final Product product;
    private final Long quantity;
    private final Long unitPrice;
    private final String description;

    public InboundItem(
            final Product product,
            final Long quantity,
            final Long unitPrice,
            final String description) {
        validateConstructor(product, quantity, unitPrice, description);
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    private void validateConstructor(
            final Product product,
            final Long quantity,
            final Long unitPrice,
            final String description) {
        Assert.notNull(product, "상품은 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (1 > quantity) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
        Assert.notNull(unitPrice, "단가는 필수입니다.");
        if (0 > unitPrice) {
            throw new IllegalArgumentException("단가는 0원 이상이어야 합니다.");
        }
        Assert.hasText(description, "품목 설명은 필수입니다.");
    }
}
