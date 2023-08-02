package com.ejoongseok.wmslive.inbound.feature.api;

import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.inbound.feature.RejectInbound;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class RejectInboundApi {
    private Long inboundNo = 1L;
    private String rejectionReason = "반려 사유";

    public RejectInboundApi inboundNo(final Long inboundNo) {
        this.inboundNo = inboundNo;
        return this;
    }

    public RejectInboundApi rejectionReason(final String rejectionReason) {
        this.rejectionReason = rejectionReason;
        return this;
    }

    public Scenario request() {
        final RejectInbound.Request request = new RejectInbound.Request(rejectionReason);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/inbounds/{inboundNo}/reject", inboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
