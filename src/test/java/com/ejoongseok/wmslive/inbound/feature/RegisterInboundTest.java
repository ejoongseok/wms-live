package com.ejoongseok.wmslive.inbound.feature;

import com.ejoongseok.wmslive.product.domain.Category;
import com.ejoongseok.wmslive.product.domain.Product;
import com.ejoongseok.wmslive.product.domain.ProductRepository;
import com.ejoongseok.wmslive.product.domain.ProductSize;
import com.ejoongseok.wmslive.product.domain.TemperatureZone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class RegisterInboundTest {

    private RegisterInbound registerInbound;
    private ProductRepository productRepository;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        inboundRepository = new InboundRepository();
        registerInbound = new RegisterInbound(productRepository, inboundRepository);
    }

    @Test
    @DisplayName("입고를 등록한다.")
    void registerInbound() {
        final Product product = new Product(
                "name",
                "code",
                "description",
                "brand",
                "maker",
                "origin",
                Category.ELECTRONICS,
                TemperatureZone.ROOM_TEMPERATURE,
                1000L,
                new ProductSize(
                        100L,
                        100L,
                        100L
                )


        );
        Mockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(product));
        final LocalDateTime orderRequestedAt = LocalDateTime.now();
        final LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1);
        final Long productNo = 1L;
        final Long quantity = 1L;
        final Long unitPrice = 1500L;
        final RegisterInbound.Request.Item inboundItem = new RegisterInbound.Request.Item(
                productNo,
                quantity,
                unitPrice,
                "description"
        );
        final List<RegisterInbound.Request.Item> inboundItems = List.of(inboundItem);
        final RegisterInbound.Request request = new RegisterInbound.Request(
                "title",
                "description",
                orderRequestedAt,
                estimatedArrivalAt,
                inboundItems
        );

        registerInbound.request(request);
    }

    private class RegisterInbound {
        private final ProductRepository productRepository;
        private final InboundRepository inboundRepository;

        private RegisterInbound(final ProductRepository productRepository, final InboundRepository inboundRepository) {
            this.productRepository = productRepository;
            this.inboundRepository = inboundRepository;
        }

        public void request(final Request request) {
            // TODO 요청을 도메인으로 변경해서 도메인을 저장한다.
            final List<InboundItem> inboundItems = request.inboundItems.stream()
                    .map(item ->
                            new InboundItem(
                                    productRepository.findById(item.productNo).orElseThrow(),
                                    item.quantity,
                                    item.unitPrice,
                                    item.description
                            ))
                    .toList();
            final Inbound inbound = new Inbound(
                    request.title,
                    request.description,
                    request.orderRequestedAt,
                    request.estimatedArrivalAt,
                    inboundItems
            );
            inboundRepository.save(inbound);
        }

        public record Request(
                String title,
                String description,
                LocalDateTime orderRequestedAt,
                LocalDateTime estimatedArrivalAt,
                List<Item> inboundItems) {
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

    private class InboundItem {
        private final Product product;
        private final Long quantity;
        private final Long unitPrice;
        private final String description;

        public InboundItem(
                final Product product,
                final Long quantity,
                final Long unitPrice,
                final String description) {
            validateConstructor(product, quantity, unitPrice, description);
            this.product = product;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.description = description;
        }

        private void validateConstructor(
                final Product product,
                final Long quantity,
                final Long unitPrice,
                final String description) {
            Assert.notNull(product, "상품은 필수입니다.");
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

    private class Inbound {
        private final String title;
        private final String description;
        private final LocalDateTime orderRequestedAt;
        private final LocalDateTime estimatedArrivalAt;
        private final List<InboundItem> inboundItems;
        @lombok.Getter
        private Long id;

        public Inbound(
                final String title,
                final String description,
                final LocalDateTime orderRequestedAt,
                final LocalDateTime estimatedArrivalAt,
                final List<InboundItem> inboundItems) {
            validateConstructor(
                    title,
                    description,
                    orderRequestedAt,
                    estimatedArrivalAt,
                    inboundItems);
            this.title = title;
            this.description = description;
            this.orderRequestedAt = orderRequestedAt;
            this.estimatedArrivalAt = estimatedArrivalAt;
            this.inboundItems = inboundItems;
        }

        private void validateConstructor(
                final String title,
                final String description,
                final LocalDateTime orderRequestedAt,
                final LocalDateTime estimatedArrivalAt,
                final List<InboundItem> inboundItems) {
            Assert.hasText(title, "입고 제목은 필수입니다.");
            Assert.hasText(description, "입고 설명은 필수입니다.");
            Assert.notNull(orderRequestedAt, "입고 요청일은 필수입니다.");
            Assert.notNull(estimatedArrivalAt, "입고 예정일은 필수입니다.");
            Assert.notEmpty(inboundItems, "입고 품목은 필수입니다.");
        }

        public void assignId(final Long id) {
            this.id = id;
        }

    }

    private class InboundRepository {
        private final Map<Long, Inbound> inbounds = new HashMap<>();
        private Long sequence = 1L;


        public void save(final Inbound inbound) {
            inbound.assignId(sequence);
            sequence++;
            inbounds.put(inbound.getId(), inbound);
        }
    }
}
