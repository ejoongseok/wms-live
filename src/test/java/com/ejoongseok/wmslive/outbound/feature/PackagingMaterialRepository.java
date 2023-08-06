package com.ejoongseok.wmslive.outbound.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PackagingMaterialRepository {
    private final Map<Long, PackagingMaterial> packagingMaterialMap = new HashMap<>();
    private Long sequence = 1L;

    public void save(final PackagingMaterial packagingMaterial) {
        packagingMaterial.assignNo(sequence);
        sequence++;
        packagingMaterialMap.put(packagingMaterial.getPackagingMaterialNo(), packagingMaterial);
    }

    public List<PackagingMaterial> findAll() {
        return new ArrayList<>(packagingMaterialMap.values());
    }
}
