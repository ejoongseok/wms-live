package com.ejoongseok.wmslive.outbound.domain;

public class OutboundSplitter {

    public Outbound splitOutbound(
            final Outbound from,
            final OutboundProducts toProducts,
            final PackagingMaterials packagingMaterials) {
        final Outbound splitted = from.split(toProducts);
        decreaseQuantity(toProducts, from.outboundProducts());
        from.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, from));
        splitted.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, splitted));
        return splitted;
    }

    private void decreaseQuantity(final OutboundProducts splitProducts, final OutboundProducts targets) {
        decreaseOrderQuantity(splitProducts, targets);
        targets.removeIfZeroQuantity();
    }

    private void decreaseOrderQuantity(final OutboundProducts splitOutboundProducts, final OutboundProducts targets) {
        for (final OutboundProduct splitProduct : splitOutboundProducts.outboundProducts()) {
            final OutboundProduct target = targets.getOutboundProductBy(splitProduct.getProductNo());
            target.decreaseOrderQuantity(splitProduct.getOrderQuantity());
        }
    }

    private PackagingMaterial getOptimalPackagingMaterial(final PackagingMaterials packagingMaterials, final Outbound splitted) {
        return packagingMaterials.findOptimalPackagingMaterial(splitted.outboundProducts().totalWeight(), splitted.outboundProducts().totalVolume())
                .orElseThrow(() -> new IllegalArgumentException("적합한 포장재가 없습니다."));
    }
}