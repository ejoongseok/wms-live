package com.ejoongseok.wmslive.product.feature.api;

import com.ejoongseok.wmslive.product.domain.Category;
import com.ejoongseok.wmslive.product.domain.TemperatureZone;
import com.ejoongseok.wmslive.product.feature.RegisterProduct;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class RegisterProductApi {
    private String name = "name";
    private String code = "code";
    private String description = "description";
    private String brand = "brand";
    private String maker = "maker";
    private String origin = "origin";
    private Category category = Category.ELECTRONICS;
    private TemperatureZone temperatureZone = TemperatureZone.ROOM_TEMPERATURE;
    private Long weightInGrams = 1000L;
    private Long widthInMillimeters = 100L;
    private Long heightInMillimeters = 100L;
    private Long lengthInMillimeters = 100L;

    public RegisterProductApi name(final String name) {
        this.name = name;
        return this;
    }

    public RegisterProductApi code(final String code) {
        this.code = code;
        return this;
    }

    public RegisterProductApi description(final String description) {
        this.description = description;
        return this;
    }

    public RegisterProductApi brand(final String brand) {
        this.brand = brand;
        return this;
    }

    public RegisterProductApi maker(final String maker) {
        this.maker = maker;
        return this;
    }

    public RegisterProductApi origin(final String origin) {
        this.origin = origin;
        return this;
    }

    public RegisterProductApi category(final Category category) {
        this.category = category;
        return this;
    }

    public RegisterProductApi temperatureZone(final TemperatureZone temperatureZone) {
        this.temperatureZone = temperatureZone;
        return this;
    }

    public RegisterProductApi weightInGrams(final Long weightInGrams) {
        this.weightInGrams = weightInGrams;
        return this;
    }

    public RegisterProductApi widthInMillimeters(final Long widthInMillimeters) {
        this.widthInMillimeters = widthInMillimeters;
        return this;
    }

    public RegisterProductApi heightInMillimeters(final Long heightInMillimeters) {
        this.heightInMillimeters = heightInMillimeters;
        return this;
    }

    public RegisterProductApi lengthInMillimeters(final Long lengthInMillimeters) {
        this.lengthInMillimeters = lengthInMillimeters;
        return this;
    }

    public void request() {
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
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}