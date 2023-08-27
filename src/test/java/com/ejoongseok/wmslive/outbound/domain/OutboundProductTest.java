package com.ejoongseok.wmslive.outbound.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ejoongseok.wmslive.location.domain.InventoriesFixture.anInventories;
import static com.ejoongseok.wmslive.location.domain.InventoryFixture.anInventory;
import static com.ejoongseok.wmslive.outbound.domain.OutboundProductFixture.anOutboundProduct;
import static org.assertj.core.api.Assertions.assertThat;

class OutboundProductTest {

    @Test
    void allocatePicking() {
        final OutboundProduct outboundProduct = anOutboundProduct().orderQuantity(10L).build();
        final Inventories inventories = createInventories();

        outboundProduct.allocatePicking(inventories);

        assertAllocatePicking(outboundProduct.getPickings());
    }

    private Inventories createInventories() {
        return anInventories()
                .inventories(
                        anInventory().inventoryQuantity(4L),
                        anInventory().inventoryQuantity(4L),
                        anInventory().inventoryQuantity(4L))
                .build();
    }

    private void assertAllocatePicking(final List<Picking> outboundProduct) {
        final List<Picking> pickings = outboundProduct;
        assertThat(pickings).hasSize(3);
        assertThat(pickings.get(0).getQuantityRequiredForPick()).isEqualTo(4L);
        assertThat(pickings.get(1).getQuantityRequiredForPick()).isEqualTo(4L);
        assertThat(pickings.get(2).getQuantityRequiredForPick()).isEqualTo(2L);
    }

    @Test
    @DisplayName("출고 상품에 대한 집품 목록을 할당한다. - 첫번째 재고로 집품목록을 만드는 경우")
    void firstInventory_createPickings() {
        final OutboundProduct outboundProduct = anOutboundProduct().build();
        final Inventories inventories = anInventories().build();

        final List<Picking> pickings = outboundProduct.createPickings(inventories);

        assertThat(pickings).hasSize(1);
    }

    @Test
    @DisplayName("출고 상품에 대한 집품 목록을 할당한다.")
    void createPickings() {
        final OutboundProduct outboundProduct = anOutboundProduct().orderQuantity(10L).build();
        final Inventories inventories = createInventories();

        final List<Picking> pickings = outboundProduct.createPickings(inventories);

        assertAllocatePicking(pickings);
    }
}