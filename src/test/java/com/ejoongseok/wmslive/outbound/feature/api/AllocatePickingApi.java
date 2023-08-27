package com.ejoongseok.wmslive.outbound.feature.api;

import com.ejoongseok.wmslive.common.Scenario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class AllocatePickingApi {
    private Long outboundNo = 1L;

    public AllocatePickingApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public Scenario request() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .post("/outbounds/{outboundNo}/allocate-picking", outboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
