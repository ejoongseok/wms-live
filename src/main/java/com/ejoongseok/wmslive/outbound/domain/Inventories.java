package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;

import java.util.Comparator;
import java.util.List;

public final class Inventories {
    private final List<Inventory> inventories;

    public Inventories(final List<Inventory> inventories) {
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

    public Inventories makeEfficientInventoriesForPicking(final Long productNo, final Long orderQuantity) {
        final List<Inventory> inventories = filterAvailableInventories(productNo);
        checkInventoryAvailability(orderQuantity, inventories);
        return new Inventories(sortEfficientInventoriesForPicking(inventories));
    }

    private List<Inventory> filterAvailableInventories(final Long productNo) {
        return inventories.stream()
                .filter(i -> i.getProductNo().equals(productNo))
                .filter(Inventory::hasInventory)
                .filter(Inventory::isFresh)
                .toList();
    }

    private void checkInventoryAvailability(final Long orderQuantity, final List<Inventory> inventories) {
        final long totalQuantity = inventories.stream()
                .mapToLong(Inventory::getInventoryQuantity)
                .sum();
        if (totalQuantity < orderQuantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
    }

    private List<Inventory> sortEfficientInventoriesForPicking(final List<Inventory> inventories) {
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
}