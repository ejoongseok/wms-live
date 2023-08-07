package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.product.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Table(name = "outbound_product")
@Comment("출고 상품")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboundProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbound_product_no")
    @Comment("출고 상품 번호")
    private Long outboundProductNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    @Comment("출고 번호")
    private Product product;
    @Column(name = "order_quantity", nullable = false)
    @Comment("주문 수량")
    private Long orderQuantity;
    @Column(name = "unit_price", nullable = false)
    @Comment("단가")
    private Long unitPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outbound_no", nullable = false)
    @Comment("출고 번호")
    private Outbound outbound;

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

    public void assignOutbound(final Outbound outbound) {
        this.outbound = outbound;
    }
}
