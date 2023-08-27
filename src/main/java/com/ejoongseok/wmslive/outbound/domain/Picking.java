package com.ejoongseok.wmslive.outbound.domain;

import lombok.Getter;

public class Picking {
    @Getter
    private Long quantityRequiredForPick = 0L;

    public Picking(final Long quantityRequiredForPick) {
        this.quantityRequiredForPick = quantityRequiredForPick;
    }
}
