package com.ejoongseok.wmslive.location.domain;

import com.ejoongseok.wmslive.inbound.domain.LPN;
import lombok.Getter;

public class LocationLPN {

    private final Location location;
    @Getter
    private final LPN lpn;
    @Getter
    private Long inventoryQuantity;

    public LocationLPN(final Location location, final LPN lpn) {
        this.location = location;
        this.lpn = lpn;
        inventoryQuantity = 1L;
    }

    public void increaseQuantity() {
        inventoryQuantity++;
    }

}
