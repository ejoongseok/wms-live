package com.ejoongseok.wmslive.common;

import com.ejoongseok.wmslive.inbound.feature.api.ConfirmInboundApi;
import com.ejoongseok.wmslive.inbound.feature.api.RegisterInboundApi;
import com.ejoongseok.wmslive.inbound.feature.api.RegisterLPNApi;
import com.ejoongseok.wmslive.inbound.feature.api.RejectInboundApi;
import com.ejoongseok.wmslive.location.feature.api.AssignInventoryApi;
import com.ejoongseok.wmslive.location.feature.api.RegisterLocationApi;
import com.ejoongseok.wmslive.outbound.feature.api.AllocatePickingApi;
import com.ejoongseok.wmslive.outbound.feature.api.AllocatePickingToteApi;
import com.ejoongseok.wmslive.outbound.feature.api.RegisterOutboundApi;
import com.ejoongseok.wmslive.outbound.feature.api.RegisterPackagingMaterialApi;
import com.ejoongseok.wmslive.outbound.feature.api.SplitBoundApi;
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

    public static RegisterOutboundApi registerOutbound() {
        return new RegisterOutboundApi();
    }

    public static SplitBoundApi splitOutbound() {
        return new SplitBoundApi();
    }

    public static AllocatePickingToteApi allocatePickingTote() {
        return new AllocatePickingToteApi();
    }

    public static AllocatePickingApi allocatePicking() {
        return new AllocatePickingApi();
    }
}
