package com.ejoongseok.wmslive.location.fature;

enum UsagePurpose {
    MOVE("이동 목적");
    private final String description;

    UsagePurpose(final String description) {
        this.description = description;
    }
}
