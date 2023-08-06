package com.ejoongseok.wmslive.location.feature.api;

import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.location.feature.AssignInventory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class AssignInventoryApi {
    private String locationBarcode = "A-1-1";
    private String lpnBarcode = "LPN-0001";

    public AssignInventoryApi locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public AssignInventoryApi lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public Scenario request() {
        final AssignInventory.Request request = new AssignInventory.Request(
                locationBarcode,
                lpnBarcode
        );
        //when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/locations/assign-inventory")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}