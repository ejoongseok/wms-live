package com.ejoongseok.wmslive.inbound.feature;

import com.ejoongseok.wmslive.inbound.domain.Inbound;
import com.ejoongseok.wmslive.inbound.domain.InboundItem;
import com.ejoongseok.wmslive.inbound.domain.InboundRepository;
import com.ejoongseok.wmslive.product.domain.ProductRepository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

class RegisterInbound {
    private final ProductRepository productRepository;
    private final InboundRepository inboundRepository;

    RegisterInbound(final ProductRepository productRepository, final InboundRepository inboundRepository) {
        this.productRepository = productRepository;
        this.inboundRepository = inboundRepository;
    }

    public void request(final Request request) {
        final Inbound inbound = createInbound(request);

        inboundRepository.save(inbound);
    }

    private Inbound createInbound(final Request request) {
        return new Inbound(
                request.title,
                request.description,
                request.orderRequestedAt,
                request.estimatedArrivalAt,
                mapToInboundItems(request)
        );
    }

    private List<InboundItem> mapToInboundItems(final Request request) {
        return request.inboundItems.stream()
                .map(this::newInboundItem)
                .toList();
    }

    private InboundItem newInboundItem(final Request.Item item) {
        return new InboundItem(
                productRepository.getBy(item.productNo),
                item.quantity,
                item.unitPrice,
                item.description
        );
    }

    public record Request(
            String title,
            String description,
            LocalDateTime orderRequestedAt,
            LocalDateTime estimatedArrivalAt,
            List<Request.Item> inboundItems) {
        public Request {
            Assert.hasText(title, "입고 제목은 필수입니다.");
            Assert.hasText(description, "입고 설명은 필수입니다.");
            Assert.notNull(orderRequestedAt, "입고 요청일은 필수입니다.");
            Assert.notNull(estimatedArrivalAt, "입고 예정일은 필수입니다.");
            Assert.notEmpty(inboundItems, "입고 품목은 필수입니다.");
        }

        public record Item(
                Long productNo,
                Long quantity,
                Long unitPrice,
                String description) {

            public Item {
                Assert.notNull(productNo, "상품 번호는 필수입니다.");
                Assert.notNull(quantity, "수량은 필수입니다.");
                if (1 > quantity) {
                    throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
                }
                Assert.notNull(unitPrice, "단가는 필수입니다.");
                if (0 > unitPrice) {
                    throw new IllegalArgumentException("단가는 0원 이상이어야 합니다.");
                }
                Assert.hasText(description, "품목 설명은 필수입니다.");
            }
        }
    }
}
