package com.ejoongseok.wmslive.outbound.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ejoongseok.wmslive.location.domain.InventoriesFixture.anInventories;
import static com.ejoongseok.wmslive.outbound.domain.OutboundProductFixture.anOutboundProduct;

class OutboundProductTest {

    @Test
    void allocatePicking() {
        final OutboundProduct outboundProduct = anOutboundProduct().build();
        final Inventories inventories = anInventories().build();

        outboundProduct.allocatePicking(inventories);

//        outboundProduct.getPickings();
    }

    @Test
    void createPickings() {
        final OutboundProduct outboundProduct = anOutboundProduct().build();
        final Inventories inventories = anInventories().build();

        final List<Picking> pickings = outboundProduct.createPickings(inventories);
    }
}