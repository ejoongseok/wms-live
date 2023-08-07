package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.outbound.domain.MaterialType;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterial;

import static com.ejoongseok.wmslive.outbound.feature.PackagingMaterialDimensionFixture.aPackagingMaterialDimension;

public class PackagingMaterialFIxture {

    private String name = "name";
    private String code = "code";
    private PackagingMaterialDimensionFixture packagingMaterialDimensionFixture = aPackagingMaterialDimension();
    private Long weightInGrams = 100L;
    private Long maxWeightInGrams = 1000L;
    private MaterialType materialType = MaterialType.CORRUGATED_BOX;

    public static PackagingMaterialFIxture aPackagingMaterial() {
        return new PackagingMaterialFIxture();
    }

    public PackagingMaterialFIxture name(final String name) {
        this.name = name;
        return this;
    }

    public PackagingMaterialFIxture code(final String code) {
        this.code = code;
        return this;
    }

    public PackagingMaterialFIxture packagingMaterialDimensionFixture(final PackagingMaterialDimensionFixture packagingMaterialDimensionFixture) {
        this.packagingMaterialDimensionFixture = packagingMaterialDimensionFixture;
        return this;
    }

    public PackagingMaterialFIxture weightInGrams(final Long weightInGrams) {
        this.weightInGrams = weightInGrams;
        return this;
    }

    public PackagingMaterialFIxture maxWeightInGrams(final Long maxWeightInGrams) {
        this.maxWeightInGrams = maxWeightInGrams;
        return this;
    }

    public PackagingMaterialFIxture materialType(final MaterialType materialType) {
        this.materialType = materialType;
        return this;
    }

    public PackagingMaterial build() {
        return new PackagingMaterial(
                name,
                code,
                packagingMaterialDimensionFixture.build(),
                weightInGrams,
                maxWeightInGrams,
                materialType
        );
    }
}