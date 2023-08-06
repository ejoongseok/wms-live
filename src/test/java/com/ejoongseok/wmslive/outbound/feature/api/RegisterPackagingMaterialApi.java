package com.ejoongseok.wmslive.outbound.feature.api;

import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.outbound.domain.MaterialType;
import com.ejoongseok.wmslive.outbound.feature.RegisterPackagingMaterial;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class RegisterPackagingMaterialApi {
    private String name = "name";
    private String code = "code";
    private Long innerWidthInMillimeters = 1000L;
    private Long innerHeightInMillimeters = 1000L;
    private Long innerLengthInMillimeters = 1000L;
    private Long outerWidthInMillimeters = 1000L;
    private Long outerHeightInMillimeters = 1000L;
    private Long outerLengthInMillimeters = 1000L;
    private Long weightInGrams = 100L;
    private Long maxWeightInGrams = 10000L;
    private MaterialType materialType = MaterialType.CORRUGATED_BOX;

    public RegisterPackagingMaterialApi name(final String name) {
        this.name = name;
        return this;
    }

    public RegisterPackagingMaterialApi code(final String code) {
        this.code = code;
        return this;
    }

    public RegisterPackagingMaterialApi innerWidthInMillimeters(final Long innerWidthInMillimeters) {
        this.innerWidthInMillimeters = innerWidthInMillimeters;
        return this;
    }

    public RegisterPackagingMaterialApi innerHeightInMillimeters(final Long innerHeightInMillimeters) {
        this.innerHeightInMillimeters = innerHeightInMillimeters;
        return this;
    }

    public RegisterPackagingMaterialApi innerLengthInMillimeters(final Long innerLengthInMillimeters) {
        this.innerLengthInMillimeters = innerLengthInMillimeters;
        return this;
    }

    public RegisterPackagingMaterialApi outerWidthInMillimeters(final Long outerWidthInMillimeters) {
        this.outerWidthInMillimeters = outerWidthInMillimeters;
        return this;
    }

    public RegisterPackagingMaterialApi outerHeightInMillimeters(final Long outerHeightInMillimeters) {
        this.outerHeightInMillimeters = outerHeightInMillimeters;
        return this;
    }

    public RegisterPackagingMaterialApi outerLengthInMillimeters(final Long outerLengthInMillimeters) {
        this.outerLengthInMillimeters = outerLengthInMillimeters;
        return this;
    }

    public RegisterPackagingMaterialApi weightInGrams(final Long weightInGrams) {
        this.weightInGrams = weightInGrams;
        return this;
    }

    public RegisterPackagingMaterialApi maxWeightInGrams(final Long maxWeightInGrams) {
        this.maxWeightInGrams = maxWeightInGrams;
        return this;
    }

    public RegisterPackagingMaterialApi materialType(final MaterialType materialType) {
        this.materialType = materialType;
        return this;
    }

    public Scenario request() {
        final RegisterPackagingMaterial.Request request = new RegisterPackagingMaterial.Request(
                name,
                code,
                innerWidthInMillimeters,
                innerHeightInMillimeters,
                innerLengthInMillimeters,
                outerWidthInMillimeters,
                outerHeightInMillimeters,
                outerLengthInMillimeters,
                weightInGrams,
                maxWeightInGrams,
                materialType
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/packaging-materials")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
        return new Scenario();
    }
}
