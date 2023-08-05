package com.ejoongseok.wmslive.location.domain;

import com.ejoongseok.wmslive.inbound.domain.LPN;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ejoongseok.wmslive.inbound.domain.LPNFixture.anLPN;

class LocationTest {


    @Test
    @DisplayName("로케이션에 LPN을 할당한다.")
    void assignLPN() {
        final Location location = createLocation();

        final LPN lpn = anLPN().build();

        location.assignLPN(lpn);

        //TODO locaiton에 locationLPN 목록이 비어있지 않은지 확인한다. 추가된 로케이션 LPN의 재고가 1이어야합니다.
    }

    private Location createLocation() {
        final String locationBarcode = "A-1-1";
        final StorageType storageType = StorageType.TOTE;
        final UsagePurpose usagePurpose = UsagePurpose.MOVE;
        return new Location(locationBarcode, storageType, usagePurpose);
    }
}