package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.common.ApiTest;
import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

class AllocatePickingTest extends ApiTest {

    @Autowired
    OutboundRepository outboundRepository;

    @BeforeEach
    void setUpAllocatePicking() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request()
                .registerLPN().request()
                .registerLocation().request()
                .registerLocation().locationBarcode("TOTE-1").request()
                .registerPackagingMaterial().request()
                .assignInventory().request()
                .registerOutbound().request()
                .allocatePickingTote().request();
    }

    @Test
    @DisplayName("출고 상품에 대한 집품 목록을 할당한다.")
    @Transactional
    void allocatePicking() {
        Scenario
                .allocatePicking().request();

        assertThat(outboundRepository.getBy(1L).getPickings()).hasSize(1);
    }


}
