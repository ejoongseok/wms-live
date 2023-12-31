package com.ejoongseok.wmslive.outbound.domain;

import java.util.Arrays;
import java.util.List;

import static com.ejoongseok.wmslive.outbound.domain.PackagingMaterialFixture.aPackagingMaterial;

public class PackagingMaterialsFixture {

    private List<PackagingMaterialFixture> packagingMaterials = List.of(aPackagingMaterial());

    public static PackagingMaterialsFixture aPackagingMaterials() {
        return new PackagingMaterialsFixture();
    }

    public PackagingMaterialsFixture packagingMaterials(final PackagingMaterialFixture... packagingMaterials) {
        this.packagingMaterials = Arrays.asList(packagingMaterials);
        return this;
    }

    public PackagingMaterials build() {
        return new PackagingMaterials(buildPackagingMaterials());
    }

    private List<PackagingMaterial> buildPackagingMaterials() {
        return packagingMaterials.stream()
                .map(PackagingMaterialFixture::build)
                .toList();
    }
}