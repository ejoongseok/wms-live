package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.ejoongseok.wmslive.inbound.domain.LPNFixture.anLPN;
import static com.ejoongseok.wmslive.location.domain.InventoriesFixture.anInventories;
import static com.ejoongseok.wmslive.location.domain.InventoryFixture.anInventory;
import static com.ejoongseok.wmslive.location.domain.LocationFixture.aLocation;
import static com.ejoongseok.wmslive.outbound.domain.OutboundFixture.anOutbound;
import static com.ejoongseok.wmslive.outbound.domain.OutboundProductFixture.anOutboundProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PickingAllocatorTest {

    private PickingAllocator pickingAllocator;

    @BeforeEach
    void setUp() {
        pickingAllocator = new PickingAllocator();
    }

    @Test
    @DisplayName(
            "출고 상품에 대한 집품 목록을 할당한다." +
                    "1. 재고는 유통기한이 가장 빠른 순서대로 할당한다." +
                    "2. 유통기한이 같으면 재고 수량이 가장 많은 순서대로 할당한다. " +
                    "3. 유통기한과 재고 수량이 같으면 로케이션 순서대로 할당한다." +
                    "재고가 충분하지 않을 경우 예외가 발생한다."
    )
    void allocatePicking() {
        final Inventories inventories = createInventories();
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().orderQuantity(10L))
                .build();

        pickingAllocator.allocatePicking(outbound, inventories);

        assertAllocatePickings(outbound, inventories);
    }

    private Inventories createInventories() {
        final LocalDateTime now = LocalDateTime.now();
        return anInventories()
                .inventories(
                        anInventory()
                                .inventoryNo(1L)
                                .inventoryQuantity(4L)
                                .lpn(anLPN().expirationAt(now.plusDays(3L)))
                                .location(aLocation().locationBarcode("A1"))
                        ,
                        anInventory()
                                .inventoryNo(2L)
                                .inventoryQuantity(3L)
                                .lpn(anLPN().expirationAt(now.plusDays(1L)))
                                .location(aLocation().locationBarcode("A2"))
                        ,
                        anInventory()
                                .inventoryNo(3L)
                                .inventoryQuantity(4L)
                                .lpn(anLPN().expirationAt(now.plusDays(1L)))
                                .location(aLocation().locationBarcode("A3"))
                        ,
                        anInventory()
                                .inventoryNo(4L)
                                .inventoryQuantity(4L)
                                .lpn(anLPN().expirationAt(now.plusDays(1L)))
                                .location(aLocation().locationBarcode("A1-1"))
                        ,
                        anInventory().inventoryNo(5L).lpn(anLPN().expirationAt(now.minusDays(1L))),
                        anInventory().inventoryNo(6L).inventoryQuantity(0L)
                )
                .build();
    }

    private void assertAllocatePickings(final Outbound outbound, final Inventories inventories) {
        final List<Picking> pickings = outbound.getPickings();
        final Picking picking1 = pickings.get(0);
        final Picking picking2 = pickings.get(1);
        final Picking picking3 = pickings.get(2);
        final Inventory inventory1 = inventories.getBy(picking1.getInventory());
        final Inventory inventory2 = inventories.getBy(picking2.getInventory());
        final Inventory inventory3 = inventories.getBy(picking3.getInventory());
        assertThat(pickings).hasSize(3);
        assertThat(picking1.getQuantityRequiredForPick()).isEqualTo(4L);
        assertThat(picking2.getQuantityRequiredForPick()).isEqualTo(4L);
        assertThat(picking3.getQuantityRequiredForPick()).isEqualTo(2L);
        assertThat(inventory1.getInventoryQuantity()).isEqualTo(0L);
        assertThat(inventory1.getLocationBarcode()).isEqualTo("A1-1");
        assertThat(inventory2.getInventoryQuantity()).isEqualTo(0L);
        assertThat(inventory2.getLocationBarcode()).isEqualTo("A3");
        assertThat(inventory3.getInventoryQuantity()).isEqualTo(1L);
        assertThat(inventory3.getLocationBarcode()).isEqualTo("A2");
    }


    @Test
    @DisplayName("재고가 충분하지 않을 경우 예외가 발생한다.")
    void fail_over_order_quantity_allocatePicking() {
        final Inventories inventories = createInventories();
        final long overQuantity = 16L;
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().orderQuantity(overQuantity))
                .build();

        assertThatThrownBy(() -> {
            pickingAllocator.allocatePicking(outbound, inventories);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }

}