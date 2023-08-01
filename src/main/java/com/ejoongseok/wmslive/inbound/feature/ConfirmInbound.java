package com.ejoongseok.wmslive.inbound.feature;

import com.ejoongseok.wmslive.inbound.domain.Inbound;
import com.ejoongseok.wmslive.inbound.domain.InboundRepository;

class ConfirmInbound {

    private final InboundRepository inboundRepository;

    ConfirmInbound(final InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
    }

    public void request(final Long inboundNo) {
        final Inbound inbound = inboundRepository.getBy(inboundNo);

        inbound.confirmed();
    }

}
