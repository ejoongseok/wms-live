package com.ejoongseok.wmslive.location.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AssignLocationLPNTest {

    private AssignLocationLPN assignLocationLPN;

    @BeforeEach
    void setUp() {
        assignLocationLPN = new AssignLocationLPN();
    }

    @Test
    @DisplayName("로케이션에 LPN을 할당한다.")
    void assignLocationLPN() {
        //given
        final String locationBarcode = "A-1-1";
        final String lpnBarcode = "LPN-1";
        final AssignLocationLPN.Request request = new AssignLocationLPN.Request(
                locationBarcode,
                lpnBarcode
        );
        //when
        assignLocationLPN.request(request);
        //then
        //TODO locaiton에 locationLPN 목록이 비어있지 않은지 확인한다.
    }

}
