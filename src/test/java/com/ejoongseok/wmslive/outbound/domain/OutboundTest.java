package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Location;
import org.junit.jupiter.api.Test;

import static com.ejoongseok.wmslive.location.domain.LocationFixture.aLocation;
import static com.ejoongseok.wmslive.outbound.domain.OutboundFixture.anOutbound;
import static org.assertj.core.api.Assertions.assertThat;

class OutboundTest {

    @Test
    void allocatePickingTote() {
        final Outbound outbound = anOutbound().build();
        final Location tote = aLocation().build();

        outbound.allocatePickingTote(tote);

        assertThat(outbound.getPickingTote()).isNotNull();
    }
}