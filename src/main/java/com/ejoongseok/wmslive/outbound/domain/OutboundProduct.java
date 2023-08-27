package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;
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

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "outbound_product")
@Comment("출고 상품")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboundProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbound_product_no")
    @Comment("출고 상품 번호")
    @Getter
    private Long outboundProductNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    @Comment("출고 번호")
    private Product product;
    @Column(name = "order_quantity", nullable = false)
    @Comment("주문 수량")
    @Getter
    private Long orderQuantity;
    @Column(name = "unit_price", nullable = false)
    @Comment("단가")
    private Long unitPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outbound_no", nullable = false)
    @Comment("출고 번호")
    private Outbound outbound;
    @Getter
    @OneToMany(mappedBy = "outboundProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Picking> pickings = new ArrayList<>();

    public OutboundProduct(
            final Product product,
            final Long orderQuantity,
            final Long unitPrice) {
        validateConstructor(product, orderQuantity, unitPrice);
        this.product = product;
        this.orderQuantity = orderQuantity;
        this.unitPrice = unitPrice;
    }

    @VisibleForTesting
    OutboundProduct(
            final Long outboundProductNo,
            final Product product,
            final Long orderQuantity,
            final Long unitPrice) {
        this(product, orderQuantity, unitPrice);
        this.outboundProductNo = outboundProductNo;
    }

    private void validateConstructor(final Product product, final Long orderQuantity, final Long unitPrice) {
        Assert.notNull(product, "상품은 필수입니다.");
        Assert.notNull(orderQuantity, "주문수량은 필수입니다.");
        if (1 > orderQuantity) throw new IllegalArgumentException("주문수량은 1개 이상이어야 합니다.");
        Assert.notNull(unitPrice, "단가는 필수입니다.");
        if (1 > unitPrice) throw new IllegalArgumentException("단가는 1원 이상이어야 합니다.");
    }

    void assignOutbound(final Outbound outbound) {
        this.outbound = outbound;
    }

    public Long getProductNo() {
        return product.getProductNo();
    }

    public OutboundProduct split(final Long splitQuantity) {
        if (splitQuantity > orderQuantity) throw new IllegalArgumentException("분할 수량은 주문 수량보다 작아야 합니다.");
        return new OutboundProduct(
                product,
                splitQuantity,
                unitPrice
        );
    }

    boolean isSameProductNo(final Long productNo) {
        return getProductNo().equals(productNo);
    }

    Long calculateOutboundProductWeight() {
        return product.getWeightInGrams() * orderQuantity;
    }

    Long calculateOutboundProductVolume() {
        return product.getProductSize().getVolume() * orderQuantity;
    }

    void decreaseOrderQuantity(final Long quantity) {
        if (quantity > orderQuantity) {
            throw new IllegalArgumentException("주문 수량보다 많은 수량을 출고할 수 없습니다.");
        }
        orderQuantity -= quantity;
    }

    boolean isZeroQuantity() {
        return 0 == orderQuantity;
    }

    public void allocatePicking(final Inventories inventories) {
        Assert.notNull(inventories, "집품을 할당하려는 재고 정보가 없습니다.");
        final Inventories pickingInventories = inventories.makeEfficientInventoriesForPicking(getProductNo(), orderQuantity);

        final List<Picking> pickings = createPickings(pickingInventories);

        allocatePickings(pickings);
    }

    List<Picking> createPickings(final Inventories inventories) {
        final Inventory firstInventory = inventories.toList().get(0);
        if (orderQuantity <= firstInventory.getInventoryQuantity()) {
            return List.of(new Picking(firstInventory, orderQuantity));
        }

        Long remainingQuantity = orderQuantity;
        final List<Picking> pickings = new ArrayList<>();
        for (final Inventory inventory : inventories.toList()) {
            if (isAllocationComplete(remainingQuantity)) {
                return pickings;
            }
            final Long quantityToAllocate = Math.min(
                    inventory.getInventoryQuantity(),
                    remainingQuantity);
            remainingQuantity -= quantityToAllocate;
            pickings.add(new Picking(inventory, quantityToAllocate));
        }
        return pickings;
    }

    private boolean isAllocationComplete(final Long remainingQuantity) {
        return 0 == remainingQuantity;
    }

    private void allocatePickings(final List<Picking> pickings) {
        this.pickings.clear();
        this.pickings.addAll(pickings);
        pickings.forEach(picking -> picking.assignOutboundProduct(this));
    }
}
