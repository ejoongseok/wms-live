package com.ejoongseok.wmslive.common;

import com.ejoongseok.wmslive.inbound.feature.api.ConfirmInboundApi;
import com.ejoongseok.wmslive.inbound.feature.api.RegisterInboundApi;
import com.ejoongseok.wmslive.inbound.feature.api.RegisterLPNApi;
import com.ejoongseok.wmslive.inbound.feature.api.RejectInboundApi;
import com.ejoongseok.wmslive.location.feature.api.AssignInventoryApi;
import com.ejoongseok.wmslive.location.feature.api.RegisterLocationApi;
import com.ejoongseok.wmslive.outbound.feature.api.RegisterOutboundApi;
import com.ejoongseok.wmslive.outbound.feature.api.RegisterPackagingMaterialApi;
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

    public static RegisterLocationApi registerLocation() {
        return new RegisterLocationApi();
    }

    public static AssignInventoryApi assignInventory() {
        return new AssignInventoryApi();
    }

    public static RegisterPackagingMaterialApi registerPackagingMaterial() {
        return new RegisterPackagingMaterialApi();
    }

    public RegisterOutboundApi registerOutbound() {
        return new RegisterOutboundApi();
    }
}
