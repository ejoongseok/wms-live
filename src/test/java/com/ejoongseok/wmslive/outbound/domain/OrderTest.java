package com.ejoongseok.wmslive.outbound.domain;

import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void totalWeight() {
        final Order order = OrderFixture.anOrder().build();
        final Long l = order.totalWeight();
        System.out.println("l = " + l);
    }
}