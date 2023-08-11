package com.ejoongseok.wmslive.outbound.domain;

public class OutboundSplitter {

    public Outbound splitOutbound(
            final Outbound outbound,
            final OutboundProducts targetProducts,
            final PackagingMaterials packagingMaterials) {
        final Outbound splitted = outbound.split(targetProducts);
        final OutboundProducts targets = outbound.outboundProducts();

        split(outbound, targetProducts, packagingMaterials, targets, splitted);

        return splitted;
    }

    private void split(
            final Outbound outbound,
            final OutboundProducts targetProducts,
            final PackagingMaterials packagingMaterials,
            final OutboundProducts targets,
            final Outbound splitted) {
        adjustTargetProductQuantities(outbound, targetProducts);
        targets.removeIfZeroQuantity();
        outbound.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, outbound));
        splitted.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, splitted));
    }

    private void adjustTargetProductQuantities(
            final Outbound outbound, final OutboundProducts targetProducts) {
        for (final OutboundProduct splitProduct : targetProducts.outboundProducts()) {
            final OutboundProduct target = outbound.getOutboundProductBy(splitProduct.getProductNo());
            target.decreaseOrderQuantity(splitProduct.getOrderQuantity());
        }
    }

    private PackagingMaterial getOptimalPackagingMaterial(
            final PackagingMaterials packagingMaterials, final Outbound outbound) {
        return packagingMaterials.findOptimalPackagingMaterial(outbound.totalWeight(), outbound.totalVolume())
                .orElseThrow(() -> new IllegalArgumentException("적합한 포장재가 없습니다."));
    }

}