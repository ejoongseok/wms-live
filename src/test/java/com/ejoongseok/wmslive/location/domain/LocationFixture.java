package com.ejoongseok.wmslive.location.domain;

public class LocationFixture {
    private String locationBarcode = "A-1-1";
    private StorageType storageType = StorageType.TOTE;
    private UsagePurpose usagePurpose = UsagePurpose.MOVE;

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

    Location build() {
        return new Location(
                locationBarcode,
                storageType,
                usagePurpose);
    }
}