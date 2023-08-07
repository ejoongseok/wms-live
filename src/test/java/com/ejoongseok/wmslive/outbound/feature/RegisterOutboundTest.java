package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.common.ApiTest;
import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterOutboundTest extends ApiTest {

    @Autowired
    private OutboundRepository outboundRepository;


    @Test
    @DisplayName("출고를 등록한다.")
    void registerOutbound() {
        Scenario.registerProduct().request();

        final Long orderNo = 1L;
        final Boolean isPriorityDelivery = false;
        final LocalDate desiredDeliveryAt = LocalDate.now();
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

        //TODO 출고가 등록되었는지 확인.
        assertThat(outboundRepository.findAll()).hasSize(1);
    }

}
