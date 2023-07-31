package com.ejoongseok.wmslive.product.fixture;

import com.ejoongseok.wmslive.product.domain.ProductSize;

public class ProductSizeFixture {
    private Long widthInMillimeters = 100L;
    private Long heightInMillimeters = 100L;
    private Long lengthInMillimeters = 100L;

    public static ProductSizeFixture aProductSize() {
        return new ProductSizeFixture();
    }

    public ProductSizeFixture widthInMillimeters(final Long widthInMillimeters) {
        this.widthInMillimeters = widthInMillimeters;
        return this;
    }

    public ProductSizeFixture heightInMillimeters(final Long heightInMillimeters) {
        this.heightInMillimeters = heightInMillimeters;
        return this;
    }

    public ProductSizeFixture lengthInMillimeters(final Long lengthInMillimeters) {
        this.lengthInMillimeters = lengthInMillimeters;
        return this;
    }

    public ProductSize build() {
        return new ProductSize(
                widthInMillimeters,
                heightInMillimeters,
                lengthInMillimeters
        );
    }
}