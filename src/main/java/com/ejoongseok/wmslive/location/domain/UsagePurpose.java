package com.ejoongseok.wmslive.location.domain;

public enum UsagePurpose {
    MOVE("이동 목적");
    private final String description;

    UsagePurpose(final String description) {
        this.description = description;
    }
}
