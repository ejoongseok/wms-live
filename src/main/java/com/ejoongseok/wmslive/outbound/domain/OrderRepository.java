package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.product.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRepository {
    private final ProductRepository productRepository;

    public OrderRepository(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Order getBy(final Long orderNo) {
        return new Order(
                orderNo,
                new OrderCustomer(
                        "name",
                        "email",
                        "phone",
                        "zipNo",
                        "address"
                ),
                "배송 요구사항",
                List.of(
                        new OrderProduct(
                                productRepository.getBy(1L),
                                1L,
                                1500L)
                ));
    }
}
