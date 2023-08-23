package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.common.ApiTest;
import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AllocatePickingToteTest extends ApiTest {

    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void setUpAllocatePickingTote() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request()
                .registerLPN().request()
                .registerLocation().request()
                .registerLocation().locationBarcode("TOTE-1").request()
                .registerPackagingMaterial().request()
                .assignInventory().request()
                .registerOutbound().request();
    }

    @Test
    @DisplayName("출고 상품을 집품할 토트를 할당한다.")
    void allocatePickingTote() {
        Scenario
                .allocatePickingTote().request();

        final Outbound outbound = outboundRepository.getBy(1L);
        assertThat(outbound.getPickingTote()).isNotNull();
    }

}
