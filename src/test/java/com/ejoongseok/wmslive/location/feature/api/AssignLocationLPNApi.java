package com.ejoongseok.wmslive.location.feature.api;

import com.ejoongseok.wmslive.location.feature.AssignLocationLPN;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public enum AssignLocationLPNApi {
    ;

    public static String assignLocationLPN() {
        final String locationBarcode = "A-1-1";
        final String lpnBarcode = "LPN-0001";
        final AssignLocationLPN.Request request = new AssignLocationLPN.Request(
                locationBarcode,
                lpnBarcode
        );
        //when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/locations/assign-lpn")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return locationBarcode;
    }
}