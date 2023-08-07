package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.product.domain.Product;
import com.ejoongseok.wmslive.product.domain.ProductRepository;
import com.ejoongseok.wmslive.product.fixture.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;

class RegisterOutboundTest {

    private RegisterOutbound registerOutbound;
    private OrderRepository orderRepository;
    private OutboundRepository outboundRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        orderRepository = new OrderRepository(productRepository);
        outboundRepository = new OutboundRepository();
        registerOutbound = new RegisterOutbound(orderRepository, outboundRepository);
    }

    @Test
    @DisplayName("출고를 등록한다.")
    void registerOutbound() {
        Mockito.when(productRepository.getBy(anyLong()))
                .thenReturn(ProductFixture.aProduct().build());
        final Long orderNo = 1L;
        final Boolean isPriorityDelivery = false;
        final LocalDate desiredDeliveryAt = LocalDate.now();
        final RegisterOutbound.Request request = new RegisterOutbound.Request(
                orderNo,
                isPriorityDelivery,
                desiredDeliveryAt
        );
        registerOutbound.request(request);

        //TODO 출고가 등록되었는지 확인.
        assertThat(outboundRepository.findAll()).hasSize(1);
    }

    private class RegisterOutbound {
        private final OrderRepository orderRepository;
        private final OutboundRepository outboundRepository;

        private RegisterOutbound(final OrderRepository orderRepository, final OutboundRepository outboundRepository) {
            this.orderRepository = orderRepository;
            this.outboundRepository = outboundRepository;
        }

        public void request(final Request request) {
            //주문 정보를 가져오고.
            final Order order = orderRepository.getBy(request.orderNo);

            // 주문정보에 맞는 상품의 재고가 충분한지 확인하고 충분하지 않으면 예외를 던진다.

            // 출고에 사용할 포장재를 선택해준다.

            // 출고를 생성하고.
            final List<OutboundProduct> outboundProducts = order.orderProducts.stream()
                    .map(orderProduct -> new OutboundProduct(
                            orderProduct.product,
                            orderProduct.orderQuantity,
                            orderProduct.unitPrice))
                    .toList();

            final Outbound outbound = new Outbound(
                    order.orderNo,
                    order.orderCustomer,
                    order.deliveryRequirements,
                    outboundProducts,
                    request.isPriorityDelivery,
                    request.desiredDeliveryAt
            );
            //출고를 등록한다.
            outboundRepository.save(outbound);
        }

        public record Request(
                Long orderNo,
                Boolean isPriorityDelivery,
                LocalDate desiredDeliveryAt) {

            public Request {
                Assert.notNull(orderNo, "주문번호는 필수입니다.");
                Assert.notNull(isPriorityDelivery, "우선출고여부는 필수입니다.");
                Assert.notNull(desiredDeliveryAt, "희망출고일은 필수입니다.");
            }
        }
    }

    private class OrderRepository {
        private final ProductRepository productRepository;

        private OrderRepository(final ProductRepository productRepository) {
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

    private class Order {
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
    }

    private class OrderCustomer {
        private final String name;
        private final String email;
        private final String phone;
        private final String zipNo;
        private final String address;

        public OrderCustomer(
                final String name,
                final String email,
                final String phone,
                final String zipNo,
                final String address) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.zipNo = zipNo;
            this.address = address;
        }
    }

    private class OrderProduct {
        private final Product product;
        private final Long orderQuantity;
        private final Long unitPrice;

        public OrderProduct(final Product product, final Long orderQuantity, final Long unitPrice) {
            this.product = product;
            this.orderQuantity = orderQuantity;
            this.unitPrice = unitPrice;
        }
    }

    private class OutboundProduct {
        private final Product product;
        private final Long orderQuantity;
        private final Long unitPrice;

        public OutboundProduct(final Product product, final Long orderQuantity, final Long unitPrice) {
            Assert.notNull(product, "상품은 필수입니다.");
            Assert.notNull(orderQuantity, "주문수량은 필수입니다.");
            if (1 > orderQuantity) throw new IllegalArgumentException("주문수량은 1개 이상이어야 합니다.");
            Assert.notNull(unitPrice, "단가는 필수입니다.");
            if (1 > unitPrice) throw new IllegalArgumentException("단가는 1원 이상이어야 합니다.");
            this.product = product;
            this.orderQuantity = orderQuantity;
            this.unitPrice = unitPrice;
        }
    }

    private class Outbound {
        private final Long orderNo;
        private final OrderCustomer orderCustomer;
        private final String deliveryRequirements;
        private final List<OutboundProduct> outboundProducts;
        private final Boolean isPriorityDelivery;
        private final LocalDate desiredDeliveryAt;
        @lombok.Getter
        private Long outboundNo;

        public Outbound(final Long orderNo, final OrderCustomer orderCustomer, final String deliveryRequirements, final List<OutboundProduct> outboundProducts, final Boolean isPriorityDelivery, final LocalDate desiredDeliveryAt) {
            Assert.notNull(orderNo, "주문번호는 필수입니다.");
            Assert.notNull(orderCustomer, "주문고객은 필수입니다.");
            Assert.notNull(deliveryRequirements, "배송요구사항은 필수입니다.");
            Assert.notEmpty(outboundProducts, "출고상품은 필수입니다.");
            Assert.notNull(isPriorityDelivery, "우선출고여부는 필수입니다.");
            Assert.notNull(desiredDeliveryAt, "희망출고일은 필수입니다.");
            this.orderNo = orderNo;
            this.orderCustomer = orderCustomer;
            this.deliveryRequirements = deliveryRequirements;
            this.outboundProducts = outboundProducts;
            this.isPriorityDelivery = isPriorityDelivery;
            this.desiredDeliveryAt = desiredDeliveryAt;
        }

        public void assignNo(final Long outboundNo) {
            this.outboundNo = outboundNo;
        }


    }

    private class OutboundRepository {
        private final Map<Long, Outbound> outbounds = new HashMap<>();
        private Long sequence = 1L;

        public void save(final Outbound outbound) {
            outbound.assignNo(sequence);
            sequence++;
            outbounds.put(outbound.getOutboundNo(), outbound);
        }

        public List<Outbound> findAll() {
            return new ArrayList<>(outbounds.values());
        }
    }
}
