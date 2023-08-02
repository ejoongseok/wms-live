package com.ejoongseok.wmslive.inbound.feature;

import com.ejoongseok.wmslive.common.ApiTest;
import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.inbound.domain.Inbound;
import com.ejoongseok.wmslive.inbound.domain.InboundRepository;
import com.ejoongseok.wmslive.inbound.domain.InboundStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class RejectInboundTest extends ApiTest {

    @Autowired
    private InboundRepository inboundRepository;


    @Test
    @DisplayName("입고를 반려/거부한다.")
    void rejectInbound() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .rejectInbound().request();

        final Inbound inbound = inboundRepository.getBy(1L);
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.REJECTED);
    }

}
