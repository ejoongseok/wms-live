package com.ejoongseok.wmslive.location.feature;

import com.ejoongseok.wmslive.common.ApiTest;
import com.ejoongseok.wmslive.location.domain.Location;
import com.ejoongseok.wmslive.location.domain.LocationLPN;
import com.ejoongseok.wmslive.location.domain.LocationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AssignLocationLPNTest extends ApiTest {

    @Autowired
    private AssignLocationLPN assignLocationLPN;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    @DisplayName("로케이션에 LPN을 할당한다.")
    void assignLocationLPN() {
        //given
        final String locationBarcode = "A-1-1";
        final String lpnBarcode = "LPN-1";
        final AssignLocationLPN.Request request = new AssignLocationLPN.Request(
                locationBarcode,
                lpnBarcode
        );
        //when
        assignLocationLPN.request(request);
        //then
        final Location location = locationRepository.getByLocationBarcode(locationBarcode);
        final List<LocationLPN> locationLPNList = location.getLocationLPNList();
        final LocationLPN locationLPN = locationLPNList.get(0);
        assertThat(locationLPNList).hasSize(1);
        assertThat(locationLPN.getInventoryQuantity()).isEqualTo(1L);

    }

}
