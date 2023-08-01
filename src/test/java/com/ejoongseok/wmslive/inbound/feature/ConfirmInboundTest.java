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

class ConfirmInboundTest {

    private ConfirmInbound confirmInbound;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        inboundRepository = Mockito.mock(InboundRepository.class);
        confirmInbound = new ConfirmInbound(inboundRepository);
    }

    @Test
    @DisplayName("입고를 승인한다.")
    void confirmInbound() {
        //given
        final Long inboundNo = 1L;
        final Inbound inbound = anInbound().build();
        Mockito.when(inboundRepository.getBy(inboundNo))
                .thenReturn(inbound);

        //when
        confirmInbound.request(inboundNo);

        //then
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }

}
