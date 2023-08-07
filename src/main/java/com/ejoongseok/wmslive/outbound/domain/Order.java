package com.ejoongseok.wmslive.outbound.domain;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Getter
public class Order {
    private final Long orderNo;
    private final OrderCustomer orderCustomer;
    private final String deliveryRequirements;
    private final List<OrderProduct> orderProducts;

    public Order(
            final Long orderNo,
            final OrderCustomer orderCustomer,
            final String deliveryRequirements,
            final List<OrderProduct> orderProducts) {

        this.orderNo = orderNo;
        this.orderCustomer = orderCustomer;
        this.deliveryRequirements = deliveryRequirements;
        this.orderProducts = orderProducts;
    }

    public Long totalWeight() {
        return orderProducts.stream()
                .mapToLong(OrderProduct::getWeight)
                .sum();
    }

    public Long totalVolume() {
        return orderProducts.stream()
                .mapToLong(OrderProduct::getVolume)
                .sum();
    }
}
