package com.ejoongseok.wmslive.outbound.feature;

enum MaterialType {
    CORRUGATED_BOX("골판지 상자");
    private final String description;

    MaterialType(final String description) {
        this.description = description;
    }
}
