package com.ejoongseok.wmslive.outbound.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.ejoongseok.wmslive.inbound.domain.LPNFixture.anLPN;
import static com.ejoongseok.wmslive.location.domain.InventoriesFixture.anInventories;
import static com.ejoongseok.wmslive.location.domain.InventoryFixture.anInventory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InventoriesTest {

    @Test
    void makeEfficientInventoriesForPicking() {
        final Inventories inventories = anInventories()
                .inventories(
                        anInventory(),
                        anInventory(),
                        anInventory(),
                        anInventory().lpn(anLPN().expirationAt(LocalDateTime.now().minusDays(1L))),
                        anInventory().inventoryQuantity(0L)
                )
                .build();

        final Inventories result = inventories.makeEfficientInventoriesForPicking(1L, 3L);

        assertThat(result.toList()).hasSize(3);
    }

    @Test
    @DisplayName("출고해야할 수량보다 출고가능한 수량이 적을 경우 예외가 발생한다.")
    void fail_no_availablity_makeEfficientInventoriesForPicking() {
        final Inventories inventories = anInventories()
                .inventories(
                        anInventory(),
                        anInventory(),
                        anInventory().lpn(anLPN().expirationAt(LocalDateTime.now().minusDays(1L))),
                        anInventory().inventoryQuantity(0L),
                        anInventory().inventoryQuantity(0L)
                )
                .build();

        assertThatThrownBy(() -> {
            inventories.makeEfficientInventoriesForPicking(1L, 3L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }
}