package com.ejoongseok.wmslive.outbound.feature;

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
    }

    private class AllocatePickingTote {
        public void request(final Request request) {

        }

        public record Request(Long outboundNo, String toteBarcode) {
        }
    }
}
