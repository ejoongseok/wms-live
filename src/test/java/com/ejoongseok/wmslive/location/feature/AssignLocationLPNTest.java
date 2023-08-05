package com.ejoongseok.wmslive.location.feature;

import com.ejoongseok.wmslive.inbound.domain.LPN;
import com.ejoongseok.wmslive.inbound.domain.LPNRepository;
import com.ejoongseok.wmslive.location.domain.Location;
import com.ejoongseok.wmslive.location.domain.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class AssignLocationLPNTest {

    private AssignLocationLPN assignLocationLPN;

    @BeforeEach
    void setUp() {
        assignLocationLPN = new AssignLocationLPN();
    }

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
        //TODO locaiton에 locationLPN 목록이 비어있지 않은지 확인한다.
    }

    private class AssignLocationLPN {
        private LocationRepository locationRepository;
        private LPNRepository lpnRepository;

        public void request(final Request request) {
            final Location location = locationRepository.getByLocationBarcode(request.locationBarcode);
            final LPN lpn = getByLPNBarcode(request);
        }

        private LPN getByLPNBarcode(final Request request) {
            return lpnRepository.findByLPNBarcode(request.lpnBarcode).orElseThrow();
        }

        public record Request(
                String locationBarcode,
                String lpnBarcode) {

            public Request {
                Assert.hasText(locationBarcode, "로케이션 바코드는 필수입니다.");
                Assert.hasText(lpnBarcode, "LPN 바코드는 필수입니다.");
            }
        }
    }
}
