package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;

public enum PickingFixture {
    ;

    public static Picking createPicking(final Inventory inventory) {
        return new Picking(inventory, 1L);
    }
}