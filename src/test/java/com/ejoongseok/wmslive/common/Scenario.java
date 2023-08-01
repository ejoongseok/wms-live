package com.ejoongseok.wmslive.common;

import com.ejoongseok.wmslive.inbound.feature.api.ConfirmInboundApi;
import com.ejoongseok.wmslive.inbound.feature.api.RegisterInboundApi;
import com.ejoongseok.wmslive.product.feature.api.RegisterProductApi;

public class Scenario {
    public static RegisterProductApi registerProduct() {
        return new RegisterProductApi();
    }

    public RegisterInboundApi registerInbound() {
        return new RegisterInboundApi();
    }

    public ConfirmInboundApi confirmInbound() {
        return new ConfirmInboundApi();
    }
}
