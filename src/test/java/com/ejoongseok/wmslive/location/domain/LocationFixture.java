package com.ejoongseok.wmslive.location.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LocationFixture {
    private String locationBarcode = "A-1-1";
    private StorageType storageType = StorageType.TOTE;
    private UsagePurpose usagePurpose = UsagePurpose.MOVE;
    private List<InventoryFixture> inventories = new ArrayList<>();


    public static LocationFixture aLocation() {
        return new LocationFixture();
    }

    public LocationFixture locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public LocationFixture storageType(final StorageType storageType) {
        this.storageType = storageType;
        return this;
    }

    public LocationFixture usagePurpose(final UsagePurpose usagePurpose) {
        this.usagePurpose = usagePurpose;
        return this;
    }

    public LocationFixture inventories(final InventoryFixture... inventories) {
        this.inventories = Arrays.asList(inventories);
        return this;
    }

    public Location build() {
        return new Location(
                locationBarcode,
                storageType,
                usagePurpose,
                buildInventories());
    }

    private List<Inventory> buildInventories() {
        return inventories.stream()
                .map(InventoryFixture::build)
                .collect(Collectors.toList());
    }
}