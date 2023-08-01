package com.ejoongseok.wmslive.inbound.feature;

import com.ejoongseok.wmslive.inbound.domain.Inbound;
import com.ejoongseok.wmslive.inbound.domain.InboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ConfirmInbound {

    private final InboundRepository inboundRepository;

    @Transactional
    @PostMapping("/inbounds/{inboundNo}/confirm")
    public void request(@PathVariable final Long inboundNo) {
        final Inbound inbound = inboundRepository.getBy(inboundNo);

        inbound.confirmed();
    }

}
