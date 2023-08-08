package com.ejoongseok.wmslive.location.domain;

import com.ejoongseok.wmslive.outbound.domain.Inventories;

import java.util.Arrays;
import java.util.List;

import static com.ejoongseok.wmslive.location.domain.InventoryFixture.anInventory;

public class InventoriesFixture {
    private List<InventoryFixture> inventories = List.of(anInventory());

    public static InventoriesFixture anInventories() {
        return new InventoriesFixture();
    }

    public Inventories build() {
        return new Inventories(buildInventories());
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