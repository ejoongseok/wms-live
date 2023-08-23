package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Location;
import com.ejoongseok.wmslive.location.domain.StorageType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ejoongseok.wmslive.location.domain.InventoryFixture.anInventory;
import static com.ejoongseok.wmslive.location.domain.LocationFixture.aLocation;
import static com.ejoongseok.wmslive.outbound.domain.OutboundFixture.anOutbound;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OutboundTest {

    @Test
    void allocatePickingTote() {
        final Outbound outbound = anOutbound()
                .pickingTote(null)
                .build();
        final Location tote = aLocation().build();

        outbound.allocatePickingTote(tote);

        assertThat(outbound.getPickingTote()).isNotNull();
    }

    @Test
    @DisplayName("null 체크")
    void allocatePickingTote2() {
        final Outbound outbound = anOutbound().build();
        final Location tote = null;

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(tote);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("출고에 할당할 토트는 필수 입니다.");
    }

    @Test
    @DisplayName("로케이션 토트가 맞는지")
    void allocatePickingTote3() {
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
    @DisplayName("토트에 상품이 담겨있지는 않은지.")
    void allocatePickingTote4() {
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
    @DisplayName("이미 출고에 토트가 할당되어 있는지.")
    void allocatePickingTote5() {
        final Outbound outbound = anOutbound().build();
        final Location tote = aLocation().build();

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(tote);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 출고에 토트가 할당되어 있습니다.");
    }
}