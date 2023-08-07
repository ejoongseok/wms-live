package com.ejoongseok.wmslive.outbound.domain;

import java.util.Arrays;
import java.util.List;

public class OrderFixture {
    private Long orderNo = 1L;
    private OrderCustomerFixture orderCustomerFixture = OrderCustomerFixture.anOrderCustomer();
    private String deliveryRequirement = "배송 요구사항";
    private List<OrderProductFixture> orderProductFixture = List.of(OrderProductFixture.anOrderProduct());

    public static OrderFixture anOrder() {
        return new OrderFixture();
    }

    public OrderFixture orderNo(final Long orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public OrderFixture orderCustomer(final OrderCustomerFixture orderCustomerFixture) {
        this.orderCustomerFixture = orderCustomerFixture;
        return this;
    }

    public OrderFixture deliveryRequirement(final String deliveryRequirement) {
        this.deliveryRequirement = deliveryRequirement;
        return this;
    }

    public OrderFixture orderProduct(final OrderProductFixture... orderProductFixture) {
        this.orderProductFixture = Arrays.asList(orderProductFixture);
        return this;
    }

    public Order build() {
        return new Order(
                orderNo,
                orderCustomerFixture.build(),
                deliveryRequirement,
                buildOrderProducts()
        );
    }

    private List<OrderProduct> buildOrderProducts() {
        return orderProductFixture.stream()
                .map(OrderProductFixture::build)
                .toList();
    }
}