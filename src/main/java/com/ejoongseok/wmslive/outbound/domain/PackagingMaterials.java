package com.ejoongseok.wmslive.outbound.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record PackagingMaterials(List<PackagingMaterial> packagingMaterials) {
    public Optional<PackagingMaterial> findOptimalPackagingMaterial(
            final Long totalWeight,
            final Long totalVolume) {
        return packagingMaterials().stream()
                .filter(pm -> pm.isAvailable(totalWeight, totalVolume))
                .min(Comparator.comparingLong(PackagingMaterial::outerVolume));
    }
}