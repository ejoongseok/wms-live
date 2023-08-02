package com.ejoongseok.wmslive.inbound.feature;

import com.ejoongseok.wmslive.inbound.domain.Inbound;
import com.ejoongseok.wmslive.inbound.domain.InboundRepository;
import com.ejoongseok.wmslive.inbound.domain.InboundStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.ejoongseok.wmslive.inbound.domain.InboundFixture.anInbound;
import static org.assertj.core.api.Assertions.assertThat;

class RejectInboundTest {

    private RejectInbound rejectInbound;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        inboundRepository = Mockito.mock(InboundRepository.class);
        rejectInbound = new RejectInbound(inboundRepository);
    }

    @Test
    @DisplayName("입고를 반려/거부한다.")
    void rejectInbound() {
        final Inbound inbound = anInbound().build();
        final Long inboundNo = 1L;
        final String rejectionReason = "반려 사유";
        final RejectInbound.Request request = new RejectInbound.Request(rejectionReason);
        Mockito.when(inboundRepository.getBy(inboundNo)).thenReturn(inbound);

        rejectInbound.request(inboundNo, request);

        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.REJECTED);
    }

}
