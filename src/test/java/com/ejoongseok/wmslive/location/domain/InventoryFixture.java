package com.ejoongseok.wmslive.location.domain;

import com.ejoongseok.wmslive.inbound.domain.LPNFixture;

import static com.ejoongseok.wmslive.inbound.domain.LPNFixture.anLPN;
import static com.ejoongseok.wmslive.location.domain.LocationFixture.aLocation;

public class InventoryFixture {
    private LocationFixture location = aLocation();
    private LPNFixture lpn = anLPN();

    public static InventoryFixture anInventory() {
        return new InventoryFixture();
    }

    public InventoryFixture location(final LocationFixture location) {
        this.location = location;
        return this;
    }

    public InventoryFixture lpn(final LPNFixture lpn) {
        this.lpn = lpn;
        return this;
    }

    public Inventory build() {
        return new Inventory(location.build(), lpn.build());
    }
}