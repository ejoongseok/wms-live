package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.common.ApiTest;
import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterOutboundTest extends ApiTest {

    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void setUpRegisterOutbound() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request()
                .registerLPN().request()
                .registerLocation().request()
                .registerPackagingMaterial().request()
                .assignInventory().request();
    }

    @Test
    @DisplayName("출고를 등록한다.")
    void registerOutbound() {
        Scenario
                .registerOutbound().request();

        assertThat(outboundRepository.findAll()).hasSize(1);
    }

}
