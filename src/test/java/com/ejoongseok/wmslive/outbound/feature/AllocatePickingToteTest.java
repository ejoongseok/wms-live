package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.location.domain.Location;
import com.ejoongseok.wmslive.location.domain.LocationRepository;
import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AllocatePickingToteTest {

    private AllocatePickingTote allocatePickingTote;

    @BeforeEach
    void setUp() {
        allocatePickingTote = new AllocatePickingTote();
    }

    @Test
    @DisplayName("출고 상품을 집품할 토트를 할당한다.")
    void allocatePickingTote() {
        final Long outboundNo = 1L;
        final String toteBarcode = "A-1-1";
        final AllocatePickingTote.Request request = new AllocatePickingTote.Request(
                outboundNo,
                toteBarcode
        );
        allocatePickingTote.request(request);
        // 실제로 outbound에 토트 바구니가 할당되었는지 확인하는 코드가 필요함.
    }

    private class AllocatePickingTote {
        private OutboundRepository outboundRepository;
        private LocationRepository locationRepository;

        public void request(final Request request) {
            final Outbound outbound = outboundRepository.getBy(request.outboundNo);
            final Location tote = locationRepository.getByLocationBarcode(request.toteBarcode);
            outbound.allocatePickingTote(tote);
        }

        public record Request(Long outboundNo, String toteBarcode) {
        }
    }
}
