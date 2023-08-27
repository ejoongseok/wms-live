package com.ejoongseok.wmslive.location.domain;

import com.ejoongseok.wmslive.inbound.domain.LPN;
import com.google.common.annotations.VisibleForTesting;
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

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Comment("재고")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_no")
    @Comment("재고 번호")
    private Long inventoryNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_no", nullable = false)
    @Comment("로케이션 번호")
    private Location location;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lpn_no", nullable = false)
    @Comment("LPN 번호")
    private LPN lpn;
    @Getter
    @Column(name = "inventory_quantity", nullable = false)
    @Comment("재고 수량")
    private Long inventoryQuantity;
    @Column(name = "product_no", nullable = false)
    @Comment("상품 번호")
    @Getter
    private Long productNo;

    public Inventory(final Location location, final LPN lpn) {
        this.location = location;
        this.lpn = lpn;
        inventoryQuantity = 1L;
        productNo = lpn.getProductNo();
    }

    @VisibleForTesting
    Inventory(final Location location, final LPN lpn, final Long inventoryQuantity) {
        this(location, lpn);
        this.inventoryQuantity = inventoryQuantity;
    }

    void increaseQuantity() {
        inventoryQuantity++;
    }

    boolean matchLpnToLocation(final LPN lpn) {
        return this.lpn.equals(lpn);
    }

    public boolean isFresh() {
        return lpn.isFresh();
    }

    public boolean hasInventory() {
        return 0L < inventoryQuantity;
    }

    public boolean hasAvailableQuantity() {
        return 0L < inventoryQuantity;
    }

    public LocalDateTime getExpirationAt() {
        return lpn.getExpirationAt();
    }
}
