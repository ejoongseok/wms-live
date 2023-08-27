package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;

import java.util.List;

public class PickingAllocator {
    public PickingAllocator() {
    }

    public void allocatePicking(final Outbound outbound, final Inventories inventories) {
        outbound.allocatePicking(inventories);
        deductAllocatedInventory(outbound.getPickings(), inventories);
    }

    public void deductAllocatedInventory(
            final List<Picking> pickings,
            final Inventories inventories) {
        for (final Picking picking : pickings) {
            final Inventory inventory = inventories.getBy(picking.getInventory());
            inventory.decreaseInventory(picking.getQuantityRequiredForPick());
        }
    }
}