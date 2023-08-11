package com.ejoongseok.wmslive.outbound.domain;

import org.junit.jupiter.api.BeforeEach;
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
    void split1() {
        final Outbound target = createSuccessOutbound();
        assertThat(target.getOutboundProducts()).hasSize(2);

        final OutboundProducts splitOutboundProducts = anOutboundProducts().build();
        final PackagingMaterials packagingMaterials = aPackagingMaterials().build();
        final Outbound splittedOutbound = outboundSplitter.splitOutbound(target, splitOutboundProducts, packagingMaterials);

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
        assertThat(target.getOutboundProducts()).hasSize(1);
        assertThat(target.getOutboundProducts().get(0).getProductNo()).isEqualTo(2L);
        assertThat(target.getRecommendedPackagingMaterial()).isNotNull();
        assertThat(splittedOutbound.getOutboundProducts()).hasSize(1);
        assertThat(splittedOutbound.getOutboundProducts().get(0).getProductNo()).isEqualTo(1L);
        assertThat(splittedOutbound.getRecommendedPackagingMaterial()).isNotNull();
    }

    @Test
    void fail_split2() {
        final Outbound target = anOutbound().build();

        assertThatThrownBy(() -> {
            final OutboundProducts splitOutboundProducts = anOutboundProducts().outboundProducts(
                    anOutboundProduct().orderQuantity(2L)).build();
            final PackagingMaterials packagingMaterials = aPackagingMaterials().build();
            outboundSplitter.splitOutbound(target, splitOutboundProducts, packagingMaterials);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("분할할 수량이 출고 수량보다 같거나 많습니다.");
    }

    @Test
    void fail_split3() {
        final Outbound target = createSuccessOutbound();

        assertThatThrownBy(() -> {
            final OutboundProducts splitOutboundProducts = anOutboundProducts().build();
            final PackagingMaterials packagingMaterials = aPackagingMaterials().packagingMaterials(aPackagingMaterial().maxWeightInGrams(1L)).build();
            outboundSplitter.splitOutbound(target, splitOutboundProducts, packagingMaterials);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("적합한 포장재가 없습니다.");
    }
}