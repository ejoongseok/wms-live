package com.ejoongseok.wmslive.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class RegisterPackagingMaterialTest {

    private RegisterPackagingMaterial registerPackagingMaterial;

    @BeforeEach
    void setUp() {
        registerPackagingMaterial = new RegisterPackagingMaterial();
    }

    @Test
    @DisplayName("포장재를 등록한다.")
    void registerPackagingMaterial() {
        final Long innerWidthInMillimeters = 1000L;
        final Long innerHeightInMillimeters = 1000L;
        final Long innerLengthInMillimeters = 1000L;
        final Long outerWidthInMillimeters = 1000L;
        final Long outerHeightInMillimeters = 1000L;
        final Long outerLengthInMillimeters = 1000L;
        final Long weightInGrams = 100L;
        final Long maxWeightInGrams = 10000L;
        final RegisterPackagingMaterial.Request request = new RegisterPackagingMaterial.Request(
                "name",
                "code",
                innerWidthInMillimeters,
                innerHeightInMillimeters,
                innerLengthInMillimeters,
                outerWidthInMillimeters,
                outerHeightInMillimeters,
                outerLengthInMillimeters,
                weightInGrams,
                maxWeightInGrams,
                MaterialType.CORRUGATED_BOX
        );

        registerPackagingMaterial.request(request);

        // TODO
//        assertThat(packagingMaterialRepository.findAll()).hasSize(1);
    }

    enum MaterialType {
        CORRUGATED_BOX("골판지 상자");
        private final String description;

        MaterialType(final String description) {
            this.description = description;
        }
    }

    private static class PackagingMaterialDimension {
        private final Long innerWidthInMillimeters;
        private final Long innerHeightInMillimeters;
        private final Long innerLengthInMillimeters;
        private final Long outerWidthInMillimeters;
        private final Long outerHeightInMillimeters;
        private final Long outerLengthInMillimeters;

        public PackagingMaterialDimension(
                final Long innerWidthInMillimeters,
                final Long innerHeightInMillimeters,
                final Long innerLengthInMillimeters,
                final Long outerWidthInMillimeters,
                final Long outerHeightInMillimeters,
                final Long outerLengthInMillimeters) {
            validateConsturctor(
                    innerWidthInMillimeters,
                    innerHeightInMillimeters,
                    innerLengthInMillimeters,
                    outerWidthInMillimeters,
                    outerHeightInMillimeters,
                    outerLengthInMillimeters);
            this.innerWidthInMillimeters = innerWidthInMillimeters;
            this.innerHeightInMillimeters = innerHeightInMillimeters;
            this.innerLengthInMillimeters = innerLengthInMillimeters;
            this.outerWidthInMillimeters = outerWidthInMillimeters;
            this.outerHeightInMillimeters = outerHeightInMillimeters;
            this.outerLengthInMillimeters = outerLengthInMillimeters;
        }

        private void validateConsturctor(
                final Long innerWidthInMillimeters,
                final Long innerHeightInMillimeters,
                final Long innerLengthInMillimeters,
                final Long outerWidthInMillimeters,
                final Long outerHeightInMillimeters,
                final Long outerLengthInMillimeters) {
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
        }
    }

    private static class PackagingMaterial {

        private final String name;
        private final String code;
        private final PackagingMaterialDimension packagingMaterialDimension;
        private final Long weightInGrams;
        private final Long maxWeightInGrams;
        private final MaterialType materialType;

        public PackagingMaterial(
                final String name,
                final String code,
                final PackagingMaterialDimension packagingMaterialDimension,
                final Long weightInGrams,
                final Long maxWeightInGrams,
                final MaterialType materialType) {
            validateConstructor(
                    name,
                    code,
                    packagingMaterialDimension,
                    weightInGrams,
                    maxWeightInGrams,
                    materialType);
            this.name = name;
            this.code = code;
            this.packagingMaterialDimension = packagingMaterialDimension;
            this.weightInGrams = weightInGrams;
            this.maxWeightInGrams = maxWeightInGrams;
            this.materialType = materialType;
        }

        private void validateConstructor(
                final String name,
                final String code,
                final PackagingMaterialDimension packagingMaterialDimension,
                final Long weightInGrams,
                final Long maxWeightInGrams,
                final MaterialType materialType) {
            Assert.hasText(name, "포장재 이름은 필수입니다.");
            Assert.hasText(code, "포장재 코드는 필수입니다.");
            Assert.notNull(packagingMaterialDimension, "포장재 치수는 필수입니다.");
            Assert.notNull(weightInGrams, "무게는 필수입니다.");
            Assert.notNull(maxWeightInGrams, "최대 무게는 필수입니다.");
            Assert.notNull(materialType, "포장재 종류는 필수입니다.");
        }
    }

    private class RegisterPackagingMaterial {
        private PackagingMaterialRepository packagingMaterialRepository;

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

    private class PackagingMaterialRepository {
        public void save(final PackagingMaterial packagingMaterial) {

        }
    }
}