package com.ejoongseok.wmslive.outbound.domain;

import org.junit.jupiter.api.Test;

import static com.ejoongseok.wmslive.location.domain.InventoriesFixture.anInventories;

class InventoriesTest {

    @Test
    void makeEfficientInventoriesForPicking() {
        final Inventories inventories = anInventories().build();

        final Inventories result = inventories.makeEfficientInventoriesForPicking(1L, 3L);

        //
    }
}