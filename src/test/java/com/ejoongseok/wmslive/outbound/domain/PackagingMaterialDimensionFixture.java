package com.ejoongseok.wmslive.outbound.domain;

public class PackagingMaterialDimensionFixture {

    private Long innerWidthInMillimeters = 1000L;
    private Long innerHeightInMillimeters = 1000L;
    private Long innerLengthInMillimeters = 1000L;
    private Long outerWidthInMillimeters = 1000L;
    private Long outerHeightInMillimeters = 1000L;
    private Long outerLengthInMillimeters = 1000L;

    public static PackagingMaterialDimensionFixture aPackagingMaterialDimension() {
        return new PackagingMaterialDimensionFixture();
    }

    public PackagingMaterialDimensionFixture innerWidthInMillimeters(final Long innerWidthInMillimeters) {
        this.innerWidthInMillimeters = innerWidthInMillimeters;
        return this;
    }

    public PackagingMaterialDimensionFixture innerHeightInMillimeters(final Long innerHeightInMillimeters) {
        this.innerHeightInMillimeters = innerHeightInMillimeters;
        return this;
    }

    public PackagingMaterialDimensionFixture innerLengthInMillimeters(final Long innerLengthInMillimeters) {
        this.innerLengthInMillimeters = innerLengthInMillimeters;
        return this;
    }

    public PackagingMaterialDimensionFixture outerWidthInMillimeters(final Long outerWidthInMillimeters) {
        this.outerWidthInMillimeters = outerWidthInMillimeters;
        return this;
    }

    public PackagingMaterialDimensionFixture outerHeightInMillimeters(final Long outerHeightInMillimeters) {
        this.outerHeightInMillimeters = outerHeightInMillimeters;
        return this;
    }

    public PackagingMaterialDimensionFixture outerLengthInMillimeters(final Long outerLengthInMillimeters) {
        this.outerLengthInMillimeters = outerLengthInMillimeters;
        return this;
    }

    public PackagingMaterialDimension build() {
        return new PackagingMaterialDimension(
                innerWidthInMillimeters,
                innerHeightInMillimeters,
                innerLengthInMillimeters,
                outerWidthInMillimeters,
                outerHeightInMillimeters,
                outerLengthInMillimeters);
    }
}