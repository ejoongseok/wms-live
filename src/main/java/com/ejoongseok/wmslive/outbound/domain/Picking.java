package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Table(name = "picking")
@Comment("집품")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Picking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("집품 번호")
    private Long pickingNo;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_no", nullable = false)
    private Inventory inventory;
    @Getter
    @Column(name = "quantity_required_for_pick", nullable = false)
    @Comment("집품해야할 수량")
    private Long quantityRequiredForPick = 0L;
    @Getter
    @Column(name = "picked_quantity", nullable = false)
    @Comment("집품한 수량")
    private final Integer pickedQuantity = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("출고 상품")
    @JoinColumn(name = "outbound_product_id")
    private OutboundProduct outboundProduct;

    public Picking(final Inventory inventory, final Long quantityRequiredForPick) {
        validateConstructor(inventory, quantityRequiredForPick);
        this.inventory = inventory;
        this.quantityRequiredForPick = quantityRequiredForPick;
    }

    private void validateConstructor(
            final Inventory inventory,
            final Long quantityRequiredForPick) {
        Assert.notNull(inventory, "집품이 할당된 재고는 필수입니다.");
        Assert.notNull(quantityRequiredForPick, "집품해야할 수량은 필수입니다.");
        Assert.isTrue(1 <= quantityRequiredForPick, "집품할 수량은 1개 이상이어야 합니다.");
    }

    void assignOutboundProduct(final OutboundProduct outboundProduct) {
        this.outboundProduct = outboundProduct;
    }
}
