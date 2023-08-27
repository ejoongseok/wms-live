package com.ejoongseok.wmslive.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AllocatePickingTest {

    private AllocatePicking allocatePicking;

    @BeforeEach
    void setUp() {
        allocatePicking = new AllocatePicking();
    }

    @Test
    @DisplayName("출고 상품에 대한 집품 목록을 할당한다.")
    void allocatePicking() {
        final Long outboundNo = 1L;
        allocatePicking.request(outboundNo);
    }

    private class AllocatePicking {
        public void request(final Long outboundNo) {
            throw new UnsupportedOperationException("Unsupported request");
        }
    }
}
