package com.ejoongseok.wmslive.outbound.feature.api;

import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.outbound.feature.RegisterOutbound;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class RegisterOutboundApi {
    private Long orderNo = 1L;
    private Boolean isPriorityDelivery = false;
    private LocalDate desiredDeliveryAt = LocalDate.now();

    public RegisterOutboundApi orderNo(final Long orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public RegisterOutboundApi isPriorityDelivery(final Boolean isPriorityDelivery) {
        this.isPriorityDelivery = isPriorityDelivery;
        return this;
    }

    public RegisterOutboundApi desiredDeliveryAt(final LocalDate desiredDeliveryAt) {
        this.desiredDeliveryAt = desiredDeliveryAt;
        return this;
    }

    public Scenario request() {
        final RegisterOutbound.Request request = new RegisterOutbound.Request(
                orderNo,
                isPriorityDelivery,
                desiredDeliveryAt
        );
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/outbounds")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
        return new Scenario();
    }
}
