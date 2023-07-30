package com.ejoongseok.wmslive.product.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterProductTest {

    private RegisterProduct registerProduct;

    @BeforeEach
    void setUp() {
        registerProduct = new RegisterProduct();
    }

    @Test
    @DisplayName("상품을 등록한다.")
    void registerProduct() {
        //given
        final String name = "name";
        final String code = "code";
        final String description = "description";
        final String brand = "brand";
        final String maker = "maker";
        final String origin = "origin";
        final Category category = Category.ELECTRONICS;
        final TemperatureZone temperatureZone = TemperatureZone.ROOM_TEMPERATURE;
        final Long weightInGrams = 1000L;
        final Long widthInMillimeters = 100L;
        final Long heightInMillimeters = 100L;
        final Long lengthInMillimeters = 100L;
        final RegisterProduct.Request request = new RegisterProduct.Request(
                name,
                code,
                description,
                brand,
                maker,
                origin,
                category,
                temperatureZone,
                weightInGrams, // gram
                widthInMillimeters, // 너비 mm
                heightInMillimeters, // 높이 mm
                lengthInMillimeters // 길이 mm
        );
        //when
        registerProduct.request(request);

        //then
//        assertThat(productRepository.findAll()).hasSize(1);
    }

    public enum Category {
        ELECTRONICS("전자 제품");
        private final String description;

        Category(final String description) {
            this.description = description;
        }
    }

    public enum TemperatureZone {
        ROOM_TEMPERATURE("상온");

        private final String description;

        TemperatureZone(final String description) {
            this.description = description;
        }
    }

    public static class RegisterProduct {
        public void request(final Request request) {
        }

        public record Request(
                String name,
                String code,
                String description,
                String brand,
                String maker,
                String origin,
                Category category,
                TemperatureZone temperatureZone,
                Long weightInGrams,
                Long widthInMillimeters,
                Long heightInMillimeters,
                Long lengthInMillimeters) {
        }
    }
}
