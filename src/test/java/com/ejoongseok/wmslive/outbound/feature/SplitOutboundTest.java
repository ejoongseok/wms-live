package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.common.ApiTest;
import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.inbound.feature.RegisterInbound;
import com.ejoongseok.wmslive.outbound.domain.Order;
import com.ejoongseok.wmslive.outbound.domain.OrderCustomer;
import com.ejoongseok.wmslive.outbound.domain.OrderProduct;
import com.ejoongseok.wmslive.outbound.domain.OrderRepository;
import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import com.ejoongseok.wmslive.product.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SplitOutboundTest extends ApiTest {

    @Autowired
    OutboundRepository outboundRepository;


    @BeforeEach
    void setUpSplitOutbound() {
        Scenario
                .registerProduct().request()
                .registerProduct().code("code2").request()
                .registerInbound()
                .inboundItems(
                        new RegisterInbound.Request.Item(
                                1L,
                                1L,
                                1500L,
                                "description"
                        ),
                        new RegisterInbound.Request.Item(
                                2L,
                                1L,
                                1500L,
                                "description"
                        ))
                .request()
                .confirmInbound().request()
                .registerLPN().request()
                .registerLPN().inboundItemNo(2L).lpnBarcode("A-1-2").request()
                .registerLocation().request()
                .registerPackagingMaterial().request()
                .assignInventory().request()
                .assignInventory().lpnBarcode("A-1-2").request()
                .registerOutbound().request();
    }


    @Test
    @DisplayName("출고를 분할한다.")
    void splitOutbound() {
        final Long outboundNo = 1L;
        assertThat(outboundRepository.getBy(outboundNo).getOutboundProducts()).hasSize(2);

        Scenario.splitOutbound().request();

        assertSplit(outboundNo);
    }

    private void assertSplit(final Long outboundNo) {
        final Outbound refresh = outboundRepository.getBy(outboundNo);
        assertThat(refresh.getOutboundProducts()).hasSize(1);
        assertThat(refresh.getOutboundProducts().get(0).getProductNo()).isEqualTo(2);
        assertThat(refresh.getRecommendedPackagingMaterial()).isNotNull();
        final Outbound splitted = outboundRepository.getBy(2L);
        assertThat(splitted.getOutboundProducts().get(0).getProductNo()).isEqualTo(1L);
        assertThat(splitted.getOutboundProducts()).hasSize(1);
        assertThat(splitted.getRecommendedPackagingMaterial()).isNotNull();
    }

    @TestConfiguration
    static class SplitOutboundTestConfiguration {
        @Bean
        @Primary
        public OrderRepository orderRepository(final ProductRepository productRepository) {
            return new OrderRepository() {
                @Override
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
                                            1500L),

                                    new OrderProduct(
                                            productRepository.getBy(2L),
                                            1L,
                                            1500L)
                            ));
                }
            };
        }
    }


}
