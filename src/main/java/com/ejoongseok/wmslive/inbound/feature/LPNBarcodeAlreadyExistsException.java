package com.ejoongseok.wmslive.inbound.feature;

public class LPNBarcodeAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "LPN 바코드 %s는 이미 존재합니다.";

    public LPNBarcodeAlreadyExistsException(final String lpnBarcode) {
        super(MESSAGE.formatted(lpnBarcode));
    }
}
