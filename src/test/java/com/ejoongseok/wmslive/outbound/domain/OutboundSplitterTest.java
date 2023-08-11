package com.ejoongseok.wmslive.outbound.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ejoongseok.wmslive.outbound.domain.OutboundFixture.anOutbound;
import static com.ejoongseok.wmslive.outbound.domain.OutboundProductFixture.anOutboundProduct;
import static com.ejoongseok.wmslive.outbound.domain.OutboundProductsFixture.anOutboundProducts;
import static com.ejoongseok.wmslive.outbound.domain.PackagingMaterialFixture.aPackagingMaterial;
import static com.ejoongseok.wmslive.outbound.domain.PackagingMaterialsFixture.aPackagingMaterials;
import static com.ejoongseok.wmslive.product.domain.ProductFixture.aProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OutboundSplitterTest {

    private OutboundSplitter outboundSplitter;

    @BeforeEach
    void setUp() {
        outboundSplitter = new OutboundSplitter();
    }

    @Test
    @DisplayName("분할할 출고 상품의 번호와 수량을 입력하면" +
            "분할된 출고 상품의 번호와 수량이 반환되고," +
            "분할된 출고 상품의 수량만큼 원본 출고 상품의 수량이 감소한다." +
            "분할된 출고 상품의 수량이 0이면 원본 출고 상품에서 제거한다.")
    void split1() {
        final Outbound target = createSuccessOutbound();
        assertThat(target.getOutboundProducts().outboundProducts()).hasSize(2);

        final OutboundProducts splitOutboundProducts = anOutboundProducts().build();
        final PackagingMaterials packagingMaterials = aPackagingMaterials().build();
        final Outbound splittedOutbound = outboundSplitter.execute(target, splitOutboundProducts, packagingMaterials);

        assertSplit(target, splittedOutbound);
    }

    private Outbound createSuccessOutbound() {
        return anOutbound()
                .outboundProducts(
                        anOutboundProduct().product(aProduct().productNo(1L)),
                        anOutboundProduct().product(aProduct().productNo(2L)))
                .build();
    }

    private void assertSplit(final Outbound target, final Outbound splittedOutbound) {
        assertThat(target.getOutboundProducts().outboundProducts()).hasSize(1);
        assertThat(target.getOutboundProducts().outboundProducts().get(0).getProductNo()).isEqualTo(2L);
        assertThat(target.getRecommendedPackagingMaterial()).isNotNull();
        assertThat(splittedOutbound.getOutboundProducts().outboundProducts()).hasSize(1);
        assertThat(splittedOutbound.getOutboundProducts().outboundProducts().get(0).getProductNo()).isEqualTo(1L);
        assertThat(splittedOutbound.getRecommendedPackagingMaterial()).isNotNull();
    }

    @Test
    @DisplayName("분할할 출고 상품의 수량이 원본 출고 상품의 수량보다 많으면 예외가 발생한다.")
    void fail_split2() {
        final Outbound target = anOutbound().build();

        assertThatThrownBy(() -> {
            final OutboundProducts splitOutboundProducts = anOutboundProducts().outboundProducts(
                    anOutboundProduct().orderQuantity(2L)).build();
            final PackagingMaterials packagingMaterials = aPackagingMaterials().build();
            outboundSplitter.execute(target, splitOutboundProducts, packagingMaterials);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("분할할 수량이 출고 수량보다 같거나 많습니다.");
    }

    @Test
    @DisplayName("분할한 출고에 할당한 적합한 포장재가 없으면 예외가 발생한다.")
    void fail_split3() {
        final Outbound target = createSuccessOutbound();

        assertThatThrownBy(() -> {
            final OutboundProducts splitOutboundProducts = anOutboundProducts().build();
            final PackagingMaterials packagingMaterials = aPackagingMaterials().packagingMaterials(aPackagingMaterial().maxWeightInGrams(1L)).build();
            outboundSplitter.execute(target, splitOutboundProducts, packagingMaterials);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("적합한 포장재가 없습니다.");
    }
}