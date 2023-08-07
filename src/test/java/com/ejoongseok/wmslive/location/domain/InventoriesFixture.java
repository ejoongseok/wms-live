package com.ejoongseok.wmslive.location.domain;

import com.ejoongseok.wmslive.outbound.feature.Inventories;

import java.util.Arrays;
import java.util.List;

public class InventoriesFixture {
    private Long orderQuantity = 1L;
    private List<InventoryFixture> inventories = List.of(InventoryFixture.anInventory());

    public static InventoriesFixture anInventories() {
        return new InventoriesFixture();
    }

    public Inventories build() {
        return new Inventories(buildInventories(), orderQuantity);
    }

    public InventoriesFixture orderQuantity(final Long orderQuantity) {
        this.orderQuantity = orderQuantity;
        return this;
    }

    public InventoriesFixture inventories(final InventoryFixture... inventories) {
        this.inventories = Arrays.asList(inventories);
        return this;
    }

    private List<Inventory> buildInventories() {
        return inventories.stream()
                .map(InventoryFixture::build)
                .toList();
    }
}