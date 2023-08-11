package com.ejoongseok.wmslive.outbound.domain;

import java.util.List;

public record OutboundProducts(List<OutboundProduct> outboundProducts) {
    long splitTotalQuantity() {
        return outboundProducts().stream()
                .mapToLong(OutboundProduct::getOrderQuantity)
                .sum();
    }
}