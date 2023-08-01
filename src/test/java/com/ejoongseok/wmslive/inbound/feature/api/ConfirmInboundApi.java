package com.ejoongseok.wmslive.inbound.feature.api;

import com.ejoongseok.wmslive.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

public class ConfirmInboundApi {

    private Long inboundNo = 1L;

    public ConfirmInboundApi inboundNo(final Long inboundNo) {
        this.inboundNo = inboundNo;
        return this;
    }

    public Scenario request() {
        RestAssured.given().log().all()
                .when()
                .post("/inbounds/{inboundNo}/confirm", inboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
