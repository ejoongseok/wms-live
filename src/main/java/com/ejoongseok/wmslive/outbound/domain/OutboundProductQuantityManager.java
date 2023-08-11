package com.ejoongseok.wmslive.outbound.domain;

public class OutboundProductQuantityManager {

    public void decreaseQuantity(final OutboundProducts splitProducts, final OutboundProducts targets) {
        decreaseOrderQuantity(splitProducts, targets);
        removeZeroQuantityProducts(targets);
    }

    void decreaseOrderQuantity(final OutboundProducts splitOutboundProducts, final OutboundProducts targets) {
        for (final OutboundProduct splitProduct : splitOutboundProducts.outboundProducts()) {
            final OutboundProduct target = targets.getOutboundProductBy(splitProduct.getProductNo());
            target.decreaseOrderQuantity(splitProduct.getOrderQuantity());
        }
    }

    void removeZeroQuantityProducts(final OutboundProducts targets) {
        targets.removeIf();
    }
}