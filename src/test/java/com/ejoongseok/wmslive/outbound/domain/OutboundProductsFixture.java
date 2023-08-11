package com.ejoongseok.wmslive.outbound.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ejoongseok.wmslive.outbound.domain.OutboundProductFixture.anOutboundProduct;

public class OutboundProductsFixture {

    List<OutboundProductFixture> outboundProducts = List.of(anOutboundProduct());

    public static OutboundProductsFixture anOutboundProducts() {
        return new OutboundProductsFixture();
    }

    public OutboundProductsFixture outboundProducts(final OutboundProductFixture... outboundProducts) {
        this.outboundProducts = Arrays.asList(outboundProducts);
        return this;
    }

    public OutboundProducts build() {
        return new OutboundProducts(buildOutboundProducts());
    }

    private List<OutboundProduct> buildOutboundProducts() {
        return outboundProducts.stream()
                .map(OutboundProductFixture::build)
                .collect(Collectors.toList());
    }
}