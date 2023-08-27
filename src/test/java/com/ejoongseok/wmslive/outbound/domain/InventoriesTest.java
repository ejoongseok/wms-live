package com.ejoongseok.wmslive.outbound.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.ejoongseok.wmslive.inbound.domain.LPNFixture.anLPN;
import static com.ejoongseok.wmslive.location.domain.InventoriesFixture.anInventories;
import static com.ejoongseok.wmslive.location.domain.InventoryFixture.anInventory;
import static org.assertj.core.api.Assertions.assertThat;

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
}