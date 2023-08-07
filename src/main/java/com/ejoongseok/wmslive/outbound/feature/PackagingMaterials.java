package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.outbound.domain.PackagingMaterial;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record PackagingMaterials(List<PackagingMaterial> packagingMaterials) {
    Optional<PackagingMaterial> findOptimalPackagingMaterial(
            final Long totalWeight,
            final Long totalVolume) {
        return packagingMaterials().stream()
                .filter(pm -> pm.isAvailable(totalWeight, totalVolume))
                .min(Comparator.comparingLong(PackagingMaterial::outerVolume));
    }
}