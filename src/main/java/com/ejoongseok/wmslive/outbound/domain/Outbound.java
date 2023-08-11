package com.ejoongseok.wmslive.outbound.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

import java.time.LocalDate;
import java.util.ArrayList;
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
    @OneToMany(mappedBy = "outbound", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OutboundProduct> outboundProducts = new ArrayList<>();
    @Column(name = "is_priority_delivery", nullable = false)
    @Comment("우선 출고 여부")
    private Boolean isPriorityDelivery;
    @Column(name = "desired_delivery_at", nullable = false)
    @Comment("희망 출고일")
    private LocalDate desiredDeliveryAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packaging_material_no")
    private PackagingMaterial recommendedPackagingMaterial;

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
        this.outboundProducts = outboundProducts;
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


    public OutboundProduct splitOutboundProduct(final Long productNo, final Long quantity) {
        final OutboundProduct outboundProduct = getOutboundProductBy(productNo);
        return outboundProduct.split(quantity);
    }

    private OutboundProduct getOutboundProductBy(final Long productNo) {
        return outboundProducts.stream()
                .filter(o -> o.isSameProductNo(productNo))
                .findFirst()
                .orElseThrow();
    }

    public Outbound split(final OutboundProducts splitOutboundProducts) {
        validateSplit(splitOutboundProducts);
        return new Outbound(
                orderNo,
                orderCustomer,
                deliveryRequirements,
                splitOutboundProducts.outboundProducts(),
                isPriorityDelivery,
                desiredDeliveryAt,
                null
        );
    }

    private void validateSplit(final OutboundProducts splitOutboundProducts) {
        final long totalOrderQuantity = calculateTotalOrderQuantity();
        final long splitTotalQuantity = splitOutboundProducts.splitTotalQuantity();
        if (totalOrderQuantity <= splitTotalQuantity) throw new IllegalArgumentException("분할할 수량이 출고 수량보다 같거나 많습니다.");
    }

    private long calculateTotalOrderQuantity() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::getOrderQuantity)
                .sum();
    }

    public Long totalWeight() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::calculateOutboundProductWeight)
                .sum();
    }

    public Long totalVolume() {
        return outboundProducts.stream()
                .mapToLong(OutboundProduct::calculateOutboundProductVolume)
                .sum();
    }

    public void assignPackagingMaterial(final PackagingMaterial optimalPackagingMaterial) {
        recommendedPackagingMaterial = optimalPackagingMaterial;
    }

    public void decreaseQuantity(final OutboundProducts outboundProducts) {
        decreaseOrderQuantity(outboundProducts);
        removeZeroQuantityProducts();
    }

    private void decreaseOrderQuantity(final OutboundProducts splitOutboundProducts) {
        for (final OutboundProduct splitProduct : splitOutboundProducts.outboundProducts()) {
            final OutboundProduct target = getOutboundProductBy(splitProduct.getProductNo());
            target.decreaseOrderQuantity(splitProduct.getOrderQuantity());
        }
    }

    private void removeZeroQuantityProducts() {
//        final List<OutboundProduct> targetProducts = outboundProducts.stream()
//                .filter(OutboundProduct::isZeroQuantity)
//                .map(o -> o)
//                .collect(Collectors.toList());
//        for (OutboundProduct targetProduct : targetProducts) {
//            targetProduct.removeOutbound();
//            outboundProducts.remove(targetProduct);
//        }
        outboundProducts.removeIf(OutboundProduct::isZeroQuantity);
//        System.out.println();
    }
}
