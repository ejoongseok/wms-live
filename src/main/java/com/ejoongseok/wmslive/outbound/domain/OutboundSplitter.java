package com.ejoongseok.wmslive.outbound.domain;

public class OutboundSplitter {

    private final OutboundProductQuantityManager outboundProductQuantityManager = new OutboundProductQuantityManager();

    public Outbound splitOutbound(
            final Outbound from,
            final OutboundProducts toProducts,
            final PackagingMaterials packagingMaterials) {
        final Outbound splitted = from.split(toProducts);
        outboundProductQuantityManager.decreaseQuantity(toProducts, from.outboundProducts());
        from.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, from));
        splitted.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, splitted));
        return splitted;
    }

    private PackagingMaterial getOptimalPackagingMaterial(final PackagingMaterials packagingMaterials, final Outbound splitted) {
        return packagingMaterials.findOptimalPackagingMaterial(splitted.outboundProducts().totalWeight(), splitted.outboundProducts().totalVolume())
                .orElseThrow(() -> new IllegalArgumentException("적합한 포장재가 없습니다."));
    }
}