package com.ejoongseok.wmslive.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterOutboundTest {

    private RegisterOutbound registerOutbound;

    @BeforeEach
    void setUp() {
        registerOutbound = new RegisterOutbound();
    }

    @Test
    @DisplayName("출고를 등록한다.")
    void registerOutbound() {
        final RegisterOutbound.Request request = new RegisterOutbound.Request();
        registerOutbound.request(request);
    }

    private class RegisterOutbound {
        public void request(final Request request) {

        }

        public record Request() {
        }
    }
}
