package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;
import org.springframework.util.Assert;

import java.util.List;

public class PickingAllocator {

    public void allocatePicking(final Outbound outbound, final Inventories inventories) {
        validate(outbound, inventories);
        outbound.allocatePicking(inventories);
        deductAllocatedInventory(outbound.getPickings(), inventories);
    }

    private void validate(final Outbound outbound, final Inventories inventories) {
        Assert.notNull(outbound, "출고 정보가 없습니다.");
        Assert.notNull(inventories, "재고 정보가 없습니다.");
    }

    private void deductAllocatedInventory(
            final List<Picking> pickings,
            final Inventories inventories) {
        for (final Picking picking : pickings) {
            final Inventory inventory = inventories.getBy(picking.getInventory());
            inventory.decreaseInventory(picking.getQuantityRequiredForPick());
        }
    }
}