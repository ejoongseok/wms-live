package com.ejoongseok.wmslive.product.domain;

public class ProductFixture {
    private Long productNo = 1L;
    private String name = "name";
    private String code = "code";
    private String description = "description";
    private String brand = "brand";
    private String maker = "maker";
    private String origin = "origin";
    private Category category = Category.ELECTRONICS;
    private TemperatureZone temperatureZone = TemperatureZone.ROOM_TEMPERATURE;
    private Long weightInGrams = 1000L;
    private ProductSizeFixture productSize = ProductSizeFixture.aProductSize();

    public static ProductFixture aProduct() {
        return new ProductFixture();
    }

    public ProductFixture productNo(final Long productNo) {
        this.productNo = productNo;
        return this;
    }

    public ProductFixture name(final String name) {
        this.name = name;
        return this;
    }

    public ProductFixture code(final String code) {
        this.code = code;
        return this;
    }

    public ProductFixture description(final String description) {
        this.description = description;
        return this;
    }

    public ProductFixture brand(final String brand) {
        this.brand = brand;
        return this;
    }

    public ProductFixture maker(final String maker) {
        this.maker = maker;
        return this;
    }

    public ProductFixture origin(final String origin) {
        this.origin = origin;
        return this;
    }

    public ProductFixture category(final Category category) {
        this.category = category;
        return this;
    }

    public ProductFixture temperatureZone(final TemperatureZone temperatureZone) {
        this.temperatureZone = temperatureZone;
        return this;
    }

    public ProductFixture weightInGrams(final Long weightInGrams) {
        this.weightInGrams = weightInGrams;
        return this;
    }

    public ProductFixture productSize(final ProductSizeFixture productSize) {
        this.productSize = productSize;
        return this;
    }

    public Product build() {
        return new Product(
                productNo,
                name,
                code,
                description,
                brand,
                maker,
                origin,
                category,
                temperatureZone,
                weightInGrams,
                productSize.build()
        );
    }
}