package com.ejoongseok.wmslive.inbound.domain;

import com.ejoongseok.wmslive.product.domain.Product;
import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inbound_item")
@Comment("입고 상품")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InboundItem {
    @Comment("상품")
    @JoinColumn(name = "product_no", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @Column(name = "quantity", nullable = false)
    @Comment("수량")
    private Long quantity;
    @Column(name = "unit_price", nullable = false)
    @Comment("단가")
    private Long unitPrice;
    @Column(name = "description", nullable = false)
    @Comment("상품설명")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbound_no", nullable = false)
    @Comment("입고 번호")
    private Inbound inbound;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("입고 상품 번호")
    @Column(name = "inbound_item_no")
    @Getter(AccessLevel.PROTECTED)
    private Long inboundItemNo;
    @OneToMany(mappedBy = "inboundItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Getter(AccessLevel.PROTECTED)
    private final List<LPN> lpnList = new ArrayList<>();

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

    @VisibleForTesting
    InboundItem(
            final Long inboundItemNo,
            final Product product,
            final Long quantity,
            final Long unitPrice,
            final String description) {
        this(product, quantity, unitPrice, description);
        this.inboundItemNo = inboundItemNo;
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

    public void assignInbound(final Inbound inbound) {
        Assert.notNull(inbound, "입고는 필수입니다.");
        this.inbound = inbound;
    }

    public void registerLPN(
            final String lpnBarcode,
            final LocalDateTime expirationAt) {
        validateRegisterLPN(lpnBarcode, expirationAt);
        lpnList.add(newLPN(lpnBarcode, expirationAt));
    }

    private void validateRegisterLPN(
            final String lpnBarcode,
            final LocalDateTime expirationAt) {
        Assert.hasText(lpnBarcode, "LPN 바코드는 필수입니다.");
        Assert.notNull(expirationAt, "유통기한은 필수입니다.");
    }

    private LPN newLPN(
            final String lpnBarcode,
            final LocalDateTime expirationAt) {
        return new LPN(
                lpnBarcode,
                expirationAt,
                this);
    }

    @VisibleForTesting
    public List<LPN> testingGetLpnList() {
        return lpnList;
    }

    public Long getProductNo() {
        return product.getProductNo();
    }
}
