package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.outbound.domain.Inventories;
import com.ejoongseok.wmslive.outbound.domain.Order;
import com.ejoongseok.wmslive.outbound.domain.OrderProduct;
import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundProduct;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterial;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterials;

import java.time.LocalDate;
import java.util.List;

public class ConsturctOutbound {
    public ConsturctOutbound() {
    }

    Outbound create(
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

    void validateInventory(final List<Inventories> inventoriesList, final List<OrderProduct> orderProducts) {
        for (final OrderProduct orderProduct : orderProducts) {
            final Inventories inventories = getInventories(inventoriesList, orderProduct);
            inventories.validateInventory(orderProduct.orderQuantity());
        }
    }

    Inventories getInventories(final List<Inventories> inventoriesList, final OrderProduct orderProduct) {
        return inventoriesList.stream()
                .filter(i -> i.equalsProductNo(orderProduct.getProductNo()))
                .findFirst()
                .orElseThrow();
    }

    Outbound newOutbound(
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

    List<OutboundProduct> mapToOutboundProducts(
            final List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(null::newOutboundProduct)
                .toList();
    }

    OutboundProduct newOutboundProduct(final OrderProduct orderProduct) {
        return new OutboundProduct(
                orderProduct.product(),
                orderProduct.orderQuantity(),
                orderProduct.unitPrice());
    }
}