package com.ejoongseok.wmslive.inbound.feature;

import com.ejoongseok.wmslive.common.ApiTest;
import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.inbound.domain.Inbound;
import com.ejoongseok.wmslive.inbound.domain.InboundItem;
import com.ejoongseok.wmslive.inbound.domain.InboundRepository;
import com.ejoongseok.wmslive.inbound.domain.LPN;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterLPNTest extends ApiTest {

    @Autowired
    private RegisterLPN registerLPN;
    @Autowired
    private InboundRepository inboundRepository;


    @Test
    @DisplayName("LPN을 등록한다.")
    @Transactional
    void registerLPN() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request();

        final Long inboundItemNo = 1L;
        final String lpnBarcode = "LPN-0001";
        final LocalDateTime expirationAt = LocalDateTime.now().plusDays(1L);
        final RegisterLPN.Request request = new RegisterLPN.Request(
                inboundItemNo,
                lpnBarcode,
                expirationAt
        );

        registerLPN.request(request);

        final Inbound inbound = inboundRepository.findByInboundItemNo(inboundItemNo).get();
        final InboundItem inboundItem = inbound.testingGetInboundItemBy(inboundItemNo);
        final List<LPN> lpnList = inboundItem.testingGetLpnList();
        assertThat(lpnList).hasSize(1);
    }

}
