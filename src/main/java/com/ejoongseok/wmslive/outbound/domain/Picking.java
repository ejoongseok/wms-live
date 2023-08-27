package com.ejoongseok.wmslive.outbound.domain;

import lombok.Getter;

public class Picking {
    @Getter
    private Long quantityRequiredForPick = 0L;
    private OutboundProduct outboundProduct;

    public Picking(final Long quantityRequiredForPick) {
        this.quantityRequiredForPick = quantityRequiredForPick;
    }

    public void assignOutboundProduct(final OutboundProduct outboundProduct) {
        this.outboundProduct = outboundProduct;
    }
}
