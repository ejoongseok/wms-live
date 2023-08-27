package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.InventoryFixture;

public class PickingFixture {

    private InventoryFixture inventory;
    private Long quantityRequiredForPick = 1L;

    public static PickingFixture aPicking() {
        return new PickingFixture();
    }

    public PickingFixture inventory(final InventoryFixture inventory) {
        this.inventory = inventory;
        return this;
    }

    public PickingFixture quantityRequiredForPick(final Long quantityRequiredForPick) {
        this.quantityRequiredForPick = quantityRequiredForPick;
        return this;
    }

    public Picking build() {
        return new Picking(
                inventory.build(),
                quantityRequiredForPick);
    }
}