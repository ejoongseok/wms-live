package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Location;
import com.ejoongseok.wmslive.location.domain.LocationFixture;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ejoongseok.wmslive.location.domain.LocationFixture.aLocation;
import static com.ejoongseok.wmslive.outbound.domain.OrderCustomerFixture.anOrderCustomer;
import static com.ejoongseok.wmslive.outbound.domain.OutboundProductFixture.anOutboundProduct;
import static com.ejoongseok.wmslive.outbound.domain.PackagingMaterialFixture.aPackagingMaterial;

public class OutboundFixture {
    private Long orderNo = 1L;
    private String deliveryRequirements = "배송 요구사항";
    private boolean isPriorityDelivery;
    private LocalDate desiredDeliveryAt = LocalDate.now();
    private List<OutboundProductFixture> outboundProducts = List.of(anOutboundProduct());
    private OrderCustomerFixture orderCustomer = anOrderCustomer();
    private PackagingMaterialFixture packagingMaterial = aPackagingMaterial();
    private LocationFixture pickingTote = aLocation();

    public static OutboundFixture anOutbound() {
        return new OutboundFixture();
    }

    public OutboundFixture orderNo(final Long orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public OutboundFixture deliveryRequirements(final String deliveryRequirements) {
        this.deliveryRequirements = deliveryRequirements;
        return this;
    }

    public OutboundFixture isPriorityDelivery(final boolean isPriorityDelivery) {
        this.isPriorityDelivery = isPriorityDelivery;
        return this;
    }

    public OutboundFixture desiredDeliveryAt(final LocalDate desiredDeliveryAt) {
        this.desiredDeliveryAt = desiredDeliveryAt;
        return this;
    }

    public OutboundFixture outboundProducts(final OutboundProductFixture... outboundProducts) {
        this.outboundProducts = Arrays.asList(outboundProducts);
        return this;
    }

    public OutboundFixture orderCustomer(final OrderCustomerFixture orderCustomer) {
        this.orderCustomer = orderCustomer;
        return this;
    }


    public OutboundFixture packagingMaterial(final PackagingMaterialFixture packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
        return this;
    }

    public OutboundFixture pickingTote(final LocationFixture pickingTote) {
        this.pickingTote = pickingTote;
        return this;
    }


    public Outbound build() {
        return new Outbound(
                orderNo,
                orderCustomer.build(),
                deliveryRequirements,
                buildOutboundProducts(),
                isPriorityDelivery,
                desiredDeliveryAt,
                packagingMaterial.build(),
                buildPickingTote()
        );
    }

    private Location buildPickingTote() {
        return null == pickingTote ? null : pickingTote.build();
    }

    private List<OutboundProduct> buildOutboundProducts() {
        return outboundProducts.stream()
                .map(OutboundProductFixture::build)
                .collect(Collectors.toList());
    }
}