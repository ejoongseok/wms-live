package com.ejoongseok.wmslive.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SplitOutboundTest {

    private SplitOutbound splitOutbound;

    @BeforeEach
    void setUp() {
        splitOutbound = new SplitOutbound();
    }

    @Test
    @DisplayName("출고를 분할한다.")
    void splitOutbound() {
        final SplitOutbound.Request request = new SplitOutbound.Request();
        splitOutbound.request(request);
    }

    private class SplitOutbound {
        public void request(final Request request) {

        }

        public record Request() {
        }
    }
}
