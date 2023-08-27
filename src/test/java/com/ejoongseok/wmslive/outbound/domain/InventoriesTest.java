package com.ejoongseok.wmslive.outbound.domain;

import org.junit.jupiter.api.Test;

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
                        anInventory()
                )
                .build();

        final Inventories result = inventories.makeEfficientInventoriesForPicking(1L, 3L);

        //
        assertThat(result.toList()).hasSize(3);
    }
}