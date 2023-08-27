package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;
import com.ejoongseok.wmslive.location.domain.Location;
import com.ejoongseok.wmslive.location.domain.StorageType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ejoongseok.wmslive.location.domain.InventoriesFixture.anInventories;
import static com.ejoongseok.wmslive.location.domain.InventoryFixture.anInventory;
import static com.ejoongseok.wmslive.location.domain.LocationFixture.aLocation;
import static com.ejoongseok.wmslive.outbound.domain.OutboundFixture.anOutbound;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OutboundTest {

    @Test
    @DisplayName("출고에 집품할 때 사용할 토트를 할당한다.")
    void allocatePickingTote() {
        final Outbound outbound = anOutbound()
                .pickingTote(null)
                .build();
        final Location tote = aLocation().build();

        outbound.allocatePickingTote(tote);

        assertThat(outbound.getPickingTote()).isNotNull();
    }

    @Test
    @DisplayName("출고에 집품할 때 사용할 토트를 null으로 할당하면 예외가 발생한다.")
    void fail_null_paramter_allocatePickingTote() {
        final Outbound outbound = anOutbound().build();
        final Location tote = null;

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(tote);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("출고에 할당할 토트는 필수 입니다.");
    }

    @Test
    @DisplayName("출고에 할당할 로케이션이 토트가 아니면 예외가 발생한다.")
    void fail_not_tote_allocatePickingTote() {
        final Outbound outbound = anOutbound().build();
        final Location pallet = aLocation()
                .storageType(StorageType.PALLET)
                .build();

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(pallet);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할당하려는 로케이션이 토트가 아닙니다.");
    }

    @Test
    @DisplayName("집품에 사용하려는 토트에 상품이 존재하는 경우 예외가 발생한다.")
    void fail_already_exsists_inventory_allocatePickingTote() {
        final Outbound outbound = anOutbound().build();
        final Location tote = aLocation()
                .inventories(anInventory())
                .build();

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(tote);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할당하려는 토트에 상품이 존재합니다.");
    }

    @Test
    @DisplayName("출고에 이미 토트바구니가 할당된 경우 재 할당하려고 하면 예외가 발생한다.")
    void fail_already_allocate_tote_allocatePickingTote() {
        final Outbound outbound = anOutbound().build();
        final Location tote = aLocation().build();

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(tote);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 출고에 토트가 할당되어 있습니다.");
    }

    @Test
    @DisplayName("출고에 사용할 포장재가 할당되어 있지 않은 상태로 토트 바구니를 할당하면 예외가 발생한다.")
    void fail_null_pacakagingMaterial_allocatePickingTote() {
        final Outbound outbound = anOutbound()
                .pickingTote(null)
                .packagingMaterial(null)
                .build();
        final Location tote = aLocation().build();

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(tote);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("포장재가 할당되어 있지 않습니다.");
    }

    @Test
    @DisplayName("출고 상품에 대한 집품 목록을 할당한다.")
    void allocatePicking() {
        final Outbound outbound = anOutbound().build();
        final Inventories inventories = anInventories().build();

        outbound.allocatePicking(inventories);

        for (final Picking picking : outbound.getPickings()) {
            final Inventory inventory = inventories.getBy(picking.getInventory());
            inventory.decreaseInventory(picking.getQuantity());
        }

//        outbound.getOutboundProductList().get(0).getPickings();
    }
}