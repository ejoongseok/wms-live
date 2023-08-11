package com.ejoongseok.wmslive.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class SplitOutboundTest {

    private SplitOutbound splitOutbound;

    @BeforeEach
    void setUp() {
        splitOutbound = new SplitOutbound();
    }

    @Test
    @DisplayName("출고를 분할한다.")
    void splitOutbound() {
        final Long outboundNo = 1L;
        final Long productNo = 1L;
        final Long quantity = 1L;
        final SplitOutbound.Request.Product product = new SplitOutbound.Request.Product(
                productNo,
                quantity
        );
        final List<SplitOutbound.Request.Product> products = List.of(product);
        final SplitOutbound.Request request = new SplitOutbound.Request(
                outboundNo,
                products
        );
        splitOutbound.request(request);
    }

    private class SplitOutbound {
        public void request(final Request request) {

        }

        public record Request(Long outboundNo, List<Product> products) {
            public record Product(Long productNo, Long quantity) {
            }
        }
    }
}
