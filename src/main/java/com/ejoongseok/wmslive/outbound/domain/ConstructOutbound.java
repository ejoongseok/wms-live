package com.ejoongseok.wmslive.outbound.domain;

import java.time.LocalDate;
import java.util.List;

public class ConstructOutbound {

    public Outbound create(
            final List<Inventories> inventoriesList,
            final PackagingMaterials packagingMaterials,
            final Order order,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        validateInventory(inventoriesList, order.orderProducts());
        return newOutbound(
                order,
                packagingMaterials.findOptimalPackagingMaterial(order.totalWeight(), order.totalVolume()).orElse(null),
                isPriorityDelivery,
                desiredDeliveryAt);
    }

    private void validateInventory(final List<Inventories> inventoriesList, final List<OrderProduct> orderProducts) {
        for (final OrderProduct orderProduct : orderProducts) {
            final Inventories inventories = getInventories(inventoriesList, orderProduct);
            inventories.validateInventory(orderProduct.orderQuantity());
        }
    }

    private Inventories getInventories(final List<Inventories> inventoriesList, final OrderProduct orderProduct) {
        return inventoriesList.stream()
                .filter(i -> i.equalsProductNo(orderProduct.getProductNo()))
                .findFirst()
                .orElseThrow();
    }

    private Outbound newOutbound(
            final Order order,
            final PackagingMaterial packagingMaterial,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        return new Outbound(
                order.orderNo(),
                order.orderCustomer(),
                order.deliveryRequirements(),
                mapToOutboundProducts(order.orderProducts()),
                isPriorityDelivery,
                desiredDeliveryAt,
                packagingMaterial
        );
    }

    private List<OutboundProduct> mapToOutboundProducts(
            final List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(this::newOutboundProduct)
                .toList();
    }

    private OutboundProduct newOutboundProduct(final OrderProduct orderProduct) {
        return new OutboundProduct(
                orderProduct.product(),
                orderProduct.orderQuantity(),
                orderProduct.unitPrice());
    }
}