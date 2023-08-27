package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;
import lombok.Getter;

public class Picking {
    @Getter
    private final Inventory inventory;
    @Getter
    private Long quantityRequiredForPick = 0L;
    private OutboundProduct outboundProduct;

    public Picking(final Inventory inventory, final Long quantityRequiredForPick) {

        this.inventory = inventory;
        this.quantityRequiredForPick = quantityRequiredForPick;
    }

    public void assignOutboundProduct(final OutboundProduct outboundProduct) {
        this.outboundProduct = outboundProduct;
    }
}
