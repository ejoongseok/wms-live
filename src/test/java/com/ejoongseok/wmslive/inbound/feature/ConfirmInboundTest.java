package com.ejoongseok.wmslive.inbound.feature;

import com.ejoongseok.wmslive.inbound.domain.Inbound;
import com.ejoongseok.wmslive.inbound.domain.InboundItem;
import com.ejoongseok.wmslive.inbound.domain.InboundRepository;
import com.ejoongseok.wmslive.inbound.domain.InboundStatus;
import com.ejoongseok.wmslive.product.fixture.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        final Inbound inbound = new Inbound(
                "상품명",
                "상품코드",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                List.of(new InboundItem(
                        ProductFixture.aProduct().build(),
                        1L,
                        1500L,
                        "description"
                ))
        );
        Mockito.when(inboundRepository.findById(inboundNo))
                .thenReturn(Optional.of(inbound));

        //when
        confirmInbound.request(inboundNo);

        //then
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }

    private class ConfirmInbound {

        private final InboundRepository inboundRepository;

        private ConfirmInbound(final InboundRepository inboundRepository) {
            this.inboundRepository = inboundRepository;
        }

        public void request(final Long inboundNo) {
            final Inbound inbound = inboundRepository.findById(inboundNo).orElseThrow();

            inbound.confirmed();
        }
    }
}
