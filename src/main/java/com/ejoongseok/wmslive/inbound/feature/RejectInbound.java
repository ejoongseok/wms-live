package com.ejoongseok.wmslive.inbound.feature;

import com.ejoongseok.wmslive.inbound.domain.Inbound;
import com.ejoongseok.wmslive.inbound.domain.InboundRepository;

class RejectInbound {
    private final InboundRepository inboundRepository;

    RejectInbound(final InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
    }

    public void request(final Long inboundNo, final Request request) {
        final Inbound inbound = inboundRepository.getBy(inboundNo);

        inbound.reject(request.rejectionReason);
    }

    public record Request(String rejectionReason) {
    }
}
