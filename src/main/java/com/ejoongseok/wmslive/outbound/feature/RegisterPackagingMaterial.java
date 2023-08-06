package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.outbound.domain.MaterialType;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterial;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterialDimension;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterialRepository;
import org.springframework.util.Assert;

class RegisterPackagingMaterial {
    private final PackagingMaterialRepository packagingMaterialRepository;

    RegisterPackagingMaterial(final PackagingMaterialRepository packagingMaterialRepository) {
        this.packagingMaterialRepository = packagingMaterialRepository;
    }

    public void request(final Request request) {
        final PackagingMaterial packagingMaterial = request.toDomain();

        packagingMaterialRepository.save(packagingMaterial);

    }

    public record Request(
            String name,
            String code,
            Long innerWidthInMillimeters,
            Long innerHeightInMillimeters,
            Long innerLengthInMillimeters,
            Long outerWidthInMillimeters,
            Long outerHeightInMillimeters,
            Long outerLengthInMillimeters,
            Long weightInGrams,
            Long maxWeightInGrams,
            MaterialType materialType) {
        public Request {
            Assert.hasText(name, "포장재 이름은 필수입니다.");
            Assert.hasText(code, "포장재 코드는 필수입니다.");
            Assert.notNull(innerWidthInMillimeters, "내부 폭은 필수입니다.");
            if (1 > innerWidthInMillimeters) {
                throw new IllegalArgumentException("내부 폭은 1mm 이상이어야 합니다.");
            }
            Assert.notNull(innerHeightInMillimeters, "내부 높이는 필수입니다.");
            if (1 > innerHeightInMillimeters) {
                throw new IllegalArgumentException("내부 높이는 1mm 이상이어야 합니다.");
            }
            Assert.notNull(innerLengthInMillimeters, "내부 길이는 필수입니다.");
            if (1 > innerLengthInMillimeters) {
                throw new IllegalArgumentException("내부 길이는 1mm 이상이어야 합니다.");
            }
            Assert.notNull(outerWidthInMillimeters, "외부 폭은 필수입니다.");
            if (1 > outerWidthInMillimeters) {
                throw new IllegalArgumentException("외부 폭은 1mm 이상이어야 합니다.");
            }
            Assert.notNull(outerHeightInMillimeters, "외부 높이는 필수입니다.");
            if (1 > outerHeightInMillimeters) {
                throw new IllegalArgumentException("외부 높이는 1mm 이상이어야 합니다.");
            }
            Assert.notNull(outerLengthInMillimeters, "외부 길이는 필수입니다.");
            if (1 > outerLengthInMillimeters) {
                throw new IllegalArgumentException("외부 길이는 1mm 이상이어야 합니다.");
            }
            Assert.notNull(weightInGrams, "무게는 필수입니다.");
            if (1 > weightInGrams) {
                throw new IllegalArgumentException("무게는 1g 이상이어야 합니다.");
            }
            Assert.notNull(maxWeightInGrams, "최대 무게는 필수입니다.");
            if (1 > maxWeightInGrams) {
                throw new IllegalArgumentException("최대 무게는 1g 이상이어야 합니다.");
            }
            Assert.notNull(materialType, "포장재 종류는 필수입니다.");
        }

        public PackagingMaterial toDomain() {
            return new PackagingMaterial(
                    name,
                    code,
                    new PackagingMaterialDimension(
                            innerWidthInMillimeters,
                            innerHeightInMillimeters,
                            innerLengthInMillimeters,
                            outerWidthInMillimeters,
                            outerHeightInMillimeters,
                            outerLengthInMillimeters
                    ),
                    weightInGrams,
                    maxWeightInGrams,
                    materialType
            );
        }

    }
}
