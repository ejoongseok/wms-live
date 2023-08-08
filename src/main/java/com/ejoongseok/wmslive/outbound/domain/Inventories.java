package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;

import java.time.LocalDateTime;
import java.util.List;

public record Inventories(List<Inventory> inventories, Long orderQuantity) {
    public void validateInventory() {
        final long totalInventoryQuantity = inventories().stream()
                .filter(i -> hasInventory(i))
                .filter(i -> i.getLpn().getExpirationAt().isAfter(LocalDateTime.now()))
                .mapToLong(Inventory::getInventoryQuantity)
                .sum();
        // 재고가 주문한 수량보다 적으면 예외를 던진다.
        if (totalInventoryQuantity < orderQuantity()) {
            throw new IllegalArgumentException(
                    "재고가 부족합니다. 재고 수량:%d, 주문 수량:%d".formatted(totalInventoryQuantity, orderQuantity()));
        }
    }

    private boolean hasInventory(final Inventory i) {
        return 0L < i.getInventoryQuantity();
    }
}