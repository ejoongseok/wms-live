package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.location.domain.InventoryRepository;
import com.ejoongseok.wmslive.outbound.domain.Inventories;
import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundProduct;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import com.ejoongseok.wmslive.outbound.domain.Picking;
import com.ejoongseok.wmslive.outbound.domain.PickingAllocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.ejoongseok.wmslive.location.domain.InventoriesFixture.anInventories;
import static com.ejoongseok.wmslive.location.domain.InventoryFixture.anInventory;
import static com.ejoongseok.wmslive.outbound.domain.OutboundFixture.anOutbound;
import static com.ejoongseok.wmslive.outbound.domain.OutboundProductFixture.anOutboundProduct;
import static org.assertj.core.api.Assertions.assertThat;

class AllocatePickingTest {

    private AllocatePicking allocatePicking;

    @BeforeEach
    void setUp() {
        allocatePicking = new AllocatePicking();
    }

    @Test
    @DisplayName("출고 상품에 대한 집품 목록을 할당한다.")
    void allocatePicking() {
        final Long outboundNo = 1L;
        allocatePicking.request(outboundNo);
    }

    @Test
    void allocatePicking_() {
        final Inventories inventories = anInventories()
                .inventories(
                        anInventory().inventoryNo(1L).inventoryQuantity(4L),
                        anInventory().inventoryNo(2L).inventoryQuantity(4L),
                        anInventory().inventoryNo(3L).inventoryQuantity(4L))
                .build();

        final Outbound outbound = anOutbound().outboundProducts(anOutboundProduct().orderQuantity(10L)).build();
        new PickingAllocator().allocatePicking(outbound, inventories);

        final List<Picking> pickings = outbound.getPickings();
        assertThat(pickings).hasSize(3);
        assertThat(pickings.get(0).getQuantityRequiredForPick()).isEqualTo(4L);
        assertThat(pickings.get(1).getQuantityRequiredForPick()).isEqualTo(4L);
        assertThat(pickings.get(2).getQuantityRequiredForPick()).isEqualTo(2L);
        assertThat(inventories.toList().get(0).getInventoryQuantity()).isEqualTo(0L);
        assertThat(inventories.toList().get(1).getInventoryQuantity()).isEqualTo(0L);
        assertThat(inventories.toList().get(2).getInventoryQuantity()).isEqualTo(2L);
    }

    private class AllocatePicking {
        private final PickingAllocator pickingAllocator = new PickingAllocator();
        private OutboundRepository outboundRepository;
        private InventoryRepository inventoryRepository;

        public void request(final Long outboundNo) {
            final Outbound outbound = outboundRepository.getBy(outboundNo);
            final List<OutboundProduct> outboundProducts = outbound.getOutboundProductList();
            final Inventories inventories = new Inventories(outboundProducts.stream()
                    .flatMap(op -> inventoryRepository.listBy(op.getProductNo()).stream())
                    .collect(Collectors.toList()));

            pickingAllocator.allocatePicking(outbound, inventories);
        }


        void deductAllocatedInventory(
                final List<Picking> pickings,
                final Inventories inventories) {
            pickingAllocator.deductAllocatedInventory(pickings, inventories);
        }

    }
}
