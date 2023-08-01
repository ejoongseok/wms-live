package com.ejoongseok.wmslive.inbound.domain;

public enum InboundStatus {
    REQUESTED("요청됨"),
    CONFIRMED("승인됨"),
    REJECTED("거부됨");

    private final String description;

    InboundStatus(final String description) {
        this.description = description;
    }
}
