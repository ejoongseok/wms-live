package com.ejoongseok.wmslive.outbound.domain;

public class OutboundSplitter {

    public Outbound splitOutbound(
            final Outbound outbound,
            final OutboundProducts splitOutboundProducts,
            final PackagingMaterials packagingMaterials) {
        final Outbound splitted = outbound.split(splitOutboundProducts);
        outbound.decreaseQuantity(splitOutboundProducts);
        outbound.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, outbound));
        splitted.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, splitted));
        return splitted;
    }

    private PackagingMaterial getOptimalPackagingMaterial(final PackagingMaterials packagingMaterials, final Outbound splitted) {
        return packagingMaterials.findOptimalPackagingMaterial(splitted.totalWeight(), splitted.totalVolume())
                .orElseThrow(() -> new IllegalArgumentException("적합한 포장재가 없습니다."));
    }
}