package com.ejoongseok.wmslive.location.domain;

import com.ejoongseok.wmslive.inbound.domain.LPN;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ejoongseok.wmslive.inbound.domain.LPNFixture.anLPN;
import static com.ejoongseok.wmslive.location.domain.LocationFixture.aLocation;
import static org.assertj.core.api.Assertions.assertThat;

class LocationTest {

    @Test
    @DisplayName("로케이션에 LPN을 할당한다.")
    void assignLPN() {
        final Location location = aLocation().build();
        final LPN lpn = anLPN().build();

        location.assignLPN(lpn);

        assertAssignLPN(location, 1L);
    }

    private void assertAssignLPN(
            final Location location,
            final Long expectedInventoryQuantity) {
        final List<LocationLPN> locationLPNList = location.getLocationLPNList();
        final LocationLPN locationLPN = locationLPNList.get(0);
        assertThat(locationLPNList).hasSize(1);
        assertThat(locationLPN.getInventoryQuantity()).isEqualTo(expectedInventoryQuantity);
    }

    @Test
    @DisplayName("로케이션에 LPN을 할당한다. 이미 LPN이 존재하면 생성하지 않고 재고만 증가시킨다.")
    void already_exsits_assignLPN() {
        final Location location = aLocation().build();
        final LPN lpn = anLPN().build();
        final LPN lpn2 = anLPN().build();

        location.assignLPN(lpn);
        location.assignLPN(lpn2);

        assertAssignLPN(location, 2L);
    }
}