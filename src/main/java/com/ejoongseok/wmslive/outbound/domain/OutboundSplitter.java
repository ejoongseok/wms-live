package com.ejoongseok.wmslive.outbound.domain;

public class OutboundSplitter {

    public Outbound execute(
            final Outbound original,
            final OutboundProducts targetProducts,
            final PackagingMaterials packagingMaterials) {
        final Outbound splitted = original.split(targetProducts);

        split(original, targetProducts, packagingMaterials, splitted);

        return splitted;
    }

    private void split(
            final Outbound original,
            final OutboundProducts targetProducts,
            final PackagingMaterials packagingMaterials,
            final Outbound splitted) {
        adjustTargetProductQuantities(original, targetProducts);
        original.removeEmptyQuantityProducts();
        original.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, original));
        splitted.assignPackagingMaterial(getOptimalPackagingMaterial(packagingMaterials, splitted));
    }

    private void adjustTargetProductQuantities(
            final Outbound original, final OutboundProducts targetProducts) {
        for (final OutboundProduct splitProduct : targetProducts.toList()) {
            final OutboundProduct originalOutboundProduct = original.getOutboundProductBy(splitProduct.getProductNo());
            originalOutboundProduct.decreaseOrderQuantity(splitProduct.getOrderQuantity());
        }
    }

    private PackagingMaterial getOptimalPackagingMaterial(
            final PackagingMaterials packagingMaterials, final Outbound outbound) {
        return packagingMaterials.findOptimalPackagingMaterial(outbound.totalWeight(), outbound.totalVolume())
                .orElseThrow(() -> new IllegalArgumentException("적합한 포장재가 없습니다."));
    }

}