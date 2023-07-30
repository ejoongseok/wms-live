package com.ejoongseok.wmslive.product.feature;

import org.springframework.util.Assert;

public class RegisterProduct {
    private final ProductRepository productRepository;

    public RegisterProduct(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void request(final Request request) {
        final Product product = request.toDomain();
        productRepository.save(product);
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

        public Request {
            Assert.hasText(name, "상품명은 필수입니다.");
            Assert.hasText(code, "상품코드는 필수입니다.");
            Assert.hasText(description, "상품설명은 필수입니다.");
            Assert.hasText(brand, "브랜드는 필수입니다.");
            Assert.hasText(maker, "제조사는 필수입니다.");
            Assert.hasText(origin, "원산지는 필수입니다.");
            Assert.notNull(category, "카테고리는 필수입니다.");
            Assert.notNull(temperatureZone, "온도대는 필수입니다.");
            Assert.notNull(weightInGrams, "무게는 필수입니다.");
            if (0 > weightInGrams) {
                throw new IllegalArgumentException("무게는 0보다 작을 수 없습니다.");
            }
            Assert.notNull(widthInMillimeters, "가로길이는 필수입니다.");
            if (0 > widthInMillimeters) {
                throw new IllegalArgumentException("가로길이는 0보다 작을 수 없습니다.");
            }
            Assert.notNull(heightInMillimeters, "세로길이는 필수입니다.");
            if (0 > heightInMillimeters) {
                throw new IllegalArgumentException("세로길이는 0보다 작을 수 없습니다.");
            }
            Assert.notNull(lengthInMillimeters, "세로길이는 필수입니다.");
            if (0 > lengthInMillimeters) {
                throw new IllegalArgumentException("세로길이는 0보다 작을 수 없습니다.");
            }
        }

        public Product toDomain() {
            return new Product(
                    name,
                    code,
                    description,
                    brand,
                    maker,
                    origin,
                    category,
                    temperatureZone,
                    weightInGrams,
                    new ProductSize(
                            widthInMillimeters,
                            heightInMillimeters,
                            lengthInMillimeters)
            );
        }

    }
}
