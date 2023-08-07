package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.outbound.domain.OrderRepository;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import com.ejoongseok.wmslive.product.domain.ProductRepository;
import com.ejoongseok.wmslive.product.fixture.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

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

}
