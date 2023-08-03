package com.ejoongseok.wmslive.common;

import com.ejoongseok.wmslive.inbound.feature.api.ConfirmInboundApi;
import com.ejoongseok.wmslive.inbound.feature.api.RegisterInboundApi;
import com.ejoongseok.wmslive.inbound.feature.api.RegisterLPNApi;
import com.ejoongseok.wmslive.inbound.feature.api.RejectInboundApi;
import com.ejoongseok.wmslive.product.feature.api.RegisterProductApi;

public class Scenario {
    public static RegisterProductApi registerProduct() {
        return new RegisterProductApi();
    }

    public static RegisterInboundApi registerInbound() {
        return new RegisterInboundApi();
    }

    public static ConfirmInboundApi confirmInbound() {
        return new ConfirmInboundApi();
    }

    public static RejectInboundApi rejectInbound() {
        return new RejectInboundApi();
    }

    public static RegisterLPNApi registerLPN() {
        return new RegisterLPNApi();
    }
}
