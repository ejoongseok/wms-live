package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundProduct;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;

class SplitOutboundTest {

    private SplitOutbound splitOutbound;

    @BeforeEach
    void setUp() {
        splitOutbound = new SplitOutbound();
    }

    @Test
    @DisplayName("출고를 분할한다.")
    void splitOutbound() {
        final Long outboundNo = 1L;
        final Long productNo = 1L;
        final Long quantity = 1L;
        final SplitOutbound.Request.Product product = new SplitOutbound.Request.Product(
                productNo,
                quantity
        );
        final List<SplitOutbound.Request.Product> products = List.of(product);
        final SplitOutbound.Request request = new SplitOutbound.Request(
                outboundNo,
                products
        );
        splitOutbound.request(request);
    }

    private class SplitOutbound {
        private OutboundRepository outboundRepository;

        public void request(final Request request) {
            final Outbound outbound = outboundRepository.findById(request.outboundNo).orElseThrow();
            final List<OutboundProduct> splitOutboundProducts = request.products.stream()
                    .map(product -> outbound.splitOutboundProduct(product.productNo, product.quantity))
                    .toList();

            final Outbound splitted = outbound.split(splitOutboundProducts);
            // 기존 출고에 새로운 포장재를 할당.
            // 분할된 출고에 포장재를 할당.

            // 분할된 출고를 저장.
        }

        public record Request(Long outboundNo, List<Product> products) {
            public Request {
                Assert.notNull(outboundNo, "출고번호가 없습니다.");
                Assert.notEmpty(products, "상품이 없습니다.");
            }

            public record Product(Long productNo, Long quantity) {
                public Product {
                    Assert.notNull(productNo, "상품번호가 없습니다.");
                    Assert.notNull(quantity, "수량이 없습니다.");
                    if (1 > quantity) throw new IllegalArgumentException("수량이 1보다 작습니다.");
                }
            }
        }
    }
}
