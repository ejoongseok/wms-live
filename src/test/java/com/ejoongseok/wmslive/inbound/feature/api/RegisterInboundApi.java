package com.ejoongseok.wmslive.inbound.feature.api;

import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.inbound.feature.RegisterInbound;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class RegisterInboundApi {
    private String title = "title";
    private String description = "description";
    private LocalDateTime orderRequestedAt = LocalDateTime.now();
    private LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1);
    private List<RegisterInbound.Request.Item> inboundItems = List.of(
            new RegisterInbound.Request.Item(
                    1L,
                    1L,
                    1500L,
                    "description"
            ));

    public RegisterInboundApi title(final String title) {
        this.title = title;
        return this;
    }

    public RegisterInboundApi description(final String description) {
        this.description = description;
        return this;
    }

    public RegisterInboundApi orderRequestedAt(final LocalDateTime orderRequestedAt) {
        this.orderRequestedAt = orderRequestedAt;
        return this;
    }

    public RegisterInboundApi estimatedArrivalAt(final LocalDateTime estimatedArrivalAt) {
        this.estimatedArrivalAt = estimatedArrivalAt;
        return this;
    }

    public RegisterInboundApi inboundItems(final RegisterInbound.Request.Item... inboundItems) {
        this.inboundItems = List.of(inboundItems);
        return this;
    }

    public Scenario request() {
        final RegisterInbound.Request request = new RegisterInbound.Request(
                title,
                description,
                orderRequestedAt,
                estimatedArrivalAt,
                inboundItems
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/inbounds")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        return new Scenario();
    }
}
