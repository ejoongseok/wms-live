package com.ejoongseok.wmslive.outbound.feature.api;

import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.outbound.feature.AllocatePickingTote;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class AllocatePickingToteApi {

    private Long outboundNo = 1L;
    private String toteBarcode = "TOTE-1";

    public AllocatePickingToteApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public AllocatePickingToteApi toteBarcode(final String toteBarcode) {
        this.toteBarcode = toteBarcode;
        return this;
    }

    public Scenario request() {
        final AllocatePickingTote.Request request = new AllocatePickingTote.Request(
                outboundNo,
                toteBarcode
        );
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/outbounds/allocate-picking-tote")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
