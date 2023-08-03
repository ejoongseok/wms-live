package com.ejoongseok.wmslive.inbound.feature.api;

import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.inbound.feature.RegisterLPN;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class RegisterLPNApi {
    private Long inboundItemNo = 1L;
    private String lpnBarcode = "LPN-0001";
    private LocalDateTime expirationAt = LocalDateTime.now().plusDays(1L);

    public RegisterLPNApi inboundItemNo(final Long inboundItemNo) {
        this.inboundItemNo = inboundItemNo;
        return this;
    }

    public RegisterLPNApi lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public RegisterLPNApi expirationAt(final LocalDateTime expirationAt) {
        this.expirationAt = expirationAt;
        return this;
    }

    public Scenario request() {
        final RegisterLPN.Request request = new RegisterLPN.Request(
                lpnBarcode,
                expirationAt
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/inbounds/inbound-items/{inboundItemNo}/lpns", inboundItemNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
