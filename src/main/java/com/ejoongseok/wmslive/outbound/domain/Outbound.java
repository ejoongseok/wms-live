package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;
import com.ejoongseok.wmslive.location.domain.Location;
import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Table(name = "outbound")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbound_no")
    @Comment("출고 번호")
    private Long outboundNo;

    @Comment("주문 번호")
    @Column(name = "order_no")
    private Long orderNo;
    @Embedded
    private OrderCustomer orderCustomer;
    @Column(name = "delivery_requirements", nullable = false)
    @Comment("배송 요구사항")
    private String deliveryRequirements;
    @Embedded
    private OutboundProducts outboundProducts;
    @Column(name = "is_priority_delivery", nullable = false)
    @Comment("우선 출고 여부")
    private Boolean isPriorityDelivery;
    @Column(name = "desired_delivery_at", nullable = false)
    @Comment("희망 출고일")
    private LocalDate desiredDeliveryAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packaging_material_no")
    private PackagingMaterial recommendedPackagingMaterial;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picking_tote_no")
    @Comment("집품할 토트 바구니")
    private Location pickingTote;

    @VisibleForTesting
    Outbound(
            final Long orderNo,
            final OrderCustomer orderCustomer,
            final String deliveryRequirements,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt,
            final PackagingMaterial recommendedPackagingMaterial,
            final Location pickingTote) {
        this(
                orderNo,
                orderCustomer,
                deliveryRequirements,
                outboundProducts,
                isPriorityDelivery,
                desiredDeliveryAt,
                recommendedPackagingMaterial);
        this.pickingTote = pickingTote;
    }

    public Outbound(
            final Long orderNo,
            final OrderCustomer orderCustomer,
            final String deliveryRequirements,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt,
            final PackagingMaterial recommendedPackagingMaterial) {
        this.recommendedPackagingMaterial = recommendedPackagingMaterial;
        validateConstructor(
                orderNo,
                orderCustomer,
                deliveryRequirements,
                outboundProducts,
                isPriorityDelivery,
                desiredDeliveryAt);
        this.orderNo = orderNo;
        this.orderCustomer = orderCustomer;
        this.deliveryRequirements = deliveryRequirements;
        this.isPriorityDelivery = isPriorityDelivery;
        this.desiredDeliveryAt = desiredDeliveryAt;
        this.outboundProducts = new OutboundProducts(outboundProducts);
        outboundProducts.forEach(outboundProduct -> outboundProduct.assignOutbound(this));
    }

    private void validateConstructor(final Long orderNo, final OrderCustomer orderCustomer, final String deliveryRequirements, final List<OutboundProduct> outboundProducts, final Boolean isPriorityDelivery, final LocalDate desiredDeliveryAt) {
        Assert.notNull(orderNo, "주문번호는 필수입니다.");
        Assert.notNull(orderCustomer, "주문고객은 필수입니다.");
        Assert.notNull(deliveryRequirements, "배송요구사항은 필수입니다.");
        Assert.notEmpty(outboundProducts, "출고상품은 필수입니다.");
        Assert.notNull(isPriorityDelivery, "우선출고여부는 필수입니다.");
        Assert.notNull(desiredDeliveryAt, "희망출고일은 필수입니다.");
    }

    public Outbound split(final OutboundProducts splitOutboundProducts) {
        validateSplit(splitOutboundProducts);
        return new Outbound(
                orderNo,
                orderCustomer,
                deliveryRequirements,
                splitOutboundProducts.toList(),
                isPriorityDelivery,
                desiredDeliveryAt,
                null
        );
    }

    private void validateSplit(final OutboundProducts splitOutboundProducts) {
        final long totalOrderQuantity = outboundProducts.calculateTotalOrderQuantity();
        final long splitTotalQuantity = splitOutboundProducts.splitTotalQuantity();
        if (totalOrderQuantity <= splitTotalQuantity) throw new IllegalArgumentException("분할할 수량이 출고 수량보다 같거나 많습니다.");
    }

    void assignPackagingMaterial(final PackagingMaterial optimalPackagingMaterial) {
        recommendedPackagingMaterial = optimalPackagingMaterial;
    }

    OutboundProducts outboundProducts() {
        return outboundProducts;
    }

    Long totalWeight() {
        return outboundProducts.totalWeight();
    }

    Long totalVolume() {
        return outboundProducts.totalVolume();
    }

    OutboundProduct getOutboundProductBy(final Long productNo) {
        return outboundProducts.getOutboundProductBy(productNo);
    }

    public OutboundProduct createOutboundProductToBeSplit(final Long productNo, final Long quantity) {
        return outboundProducts.createOutboundProductToBeSplit(productNo, quantity);
    }

    void removeEmptyQuantityProducts() {
        outboundProducts.removeIfZeroQuantity();
    }

    public void allocatePickingTote(final Location tote) {
        validateToteAllocation(tote);
        pickingTote = tote;
    }

    private void validateToteAllocation(final Location tote) {
        Assert.notNull(tote, "출고에 할당할 토트는 필수 입니다.");
        if (!tote.isTote()) {
            throw new IllegalArgumentException("할당하려는 로케이션이 토트가 아닙니다.");
        }
        if (tote.hasAvailableInventory()) {
            throw new IllegalArgumentException("할당하려는 토트에 상품이 존재합니다.");
        }
        if (null != pickingTote) {
            throw new IllegalStateException("이미 출고에 토트가 할당되어 있습니다.");
        }
        if (null == recommendedPackagingMaterial) {
            throw new IllegalStateException("포장재가 할당되어 있지 않습니다.");
        }
    }

    public List<OutboundProduct> getOutboundProductList() {
        return outboundProducts.toList();
    }

    public void allocatePicking(final List<Inventory> inventories) {
        throw new UnsupportedOperationException("Unsupported allocatePicking");
    }
}
