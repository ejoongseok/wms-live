package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;

public final class Inventories {
    private final List<Inventory> inventories;

    public Inventories(final List<Inventory> inventories) {
        Assert.notEmpty(inventories, "재고 정보가 없습니다.");
        this.inventories = inventories;
    }

    public void validateInventory(final Long orderQuantity) {
        final long totalInventoryQuantity = calculateTotalInventory();
        // 재고가 주문한 수량보다 적으면 예외를 던진다.
        if (totalInventoryQuantity < orderQuantity) {
            throw new IllegalArgumentException(
                    "재고가 부족합니다. 재고 수량:%d, 주문 수량:%d".formatted(totalInventoryQuantity, orderQuantity));
        }
    }

    private long calculateTotalInventory() {
        return inventories.stream()
                .filter(Inventory::hasInventory)
                .filter(Inventory::isFresh)
                .mapToLong(Inventory::getInventoryQuantity)
                .sum();
    }

    public boolean equalsProductNo(final Long productNo) {
        return inventories.stream()
                .anyMatch(inventory -> inventory.getProductNo().equals(productNo));
    }

    public Inventories makeEfficientInventoriesForPicking(
            final Long productNo, final Long orderQuantity) {
        validate(productNo, orderQuantity);
        final List<Inventory> inventories = filterAvailableInventories(productNo);
        checkInventoryAvailability(orderQuantity, inventories);
        return new Inventories(sortEfficientInventoriesForPicking(inventories));
    }

    private void validate(final Long productNo, final Long orderQuantity) {
        Assert.notNull(productNo, "상품 번호가 없습니다.");
        Assert.notNull(orderQuantity, "주문 수량이 없습니다.");
        if (0 >= orderQuantity) throw new IllegalArgumentException("주문 수량은 0보다 커야 합니다.");
    }

    private List<Inventory> filterAvailableInventories(final Long productNo) {
        return inventories.stream()
                .filter(i -> i.getProductNo().equals(productNo))
                .filter(Inventory::hasInventory)
                .filter(Inventory::isFresh)
                .toList();
    }

    private void checkInventoryAvailability(
            final Long orderQuantity, final List<Inventory> inventories) {
        final long totalQuantity = inventories.stream()
                .mapToLong(Inventory::getInventoryQuantity)
                .sum();
        if (totalQuantity < orderQuantity) {
            throw new IllegalArgumentException(
                    "재고가 부족합니다. 재고 수량:%d, 주문 수량:%d"
                            .formatted(totalQuantity, orderQuantity));
        }
    }

    private List<Inventory> sortEfficientInventoriesForPicking(
            final List<Inventory> inventories) {
        return inventories.stream()
                .sorted(Comparator.comparing(Inventory::getExpirationAt)
                        .thenComparing(Inventory::getInventoryQuantity, Comparator.reverseOrder())
                        .thenComparing(Inventory::getLocationBarcode)
                )
                .toList();
    }

    public List<Inventory> toList() {
        return inventories;
    }

    public Inventory getBy(final Inventory inventory) {
        return inventories.stream()
                .filter(i -> i.equals(inventory))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 재고가 존재하지 않습니다."));
    }
}