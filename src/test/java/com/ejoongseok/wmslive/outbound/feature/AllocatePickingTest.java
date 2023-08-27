package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.location.domain.InventoryRepository;
import com.ejoongseok.wmslive.outbound.domain.Inventories;
import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundProduct;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

class AllocatePickingTest {

    private AllocatePicking allocatePicking;

    @BeforeEach
    void setUp() {
        allocatePicking = new AllocatePicking();
    }

    @Test
    @DisplayName("출고 상품에 대한 집품 목록을 할당한다.")
    void allocatePicking() {
        final Long outboundNo = 1L;
        allocatePicking.request(outboundNo);
    }

    private class AllocatePicking {
        private OutboundRepository outboundRepository;
        private InventoryRepository inventoryRepository;

        public void request(final Long outboundNo) {
            final Outbound outbound = outboundRepository.getBy(outboundNo);
            final List<OutboundProduct> outboundProducts = outbound.getOutboundProductList();
            final Inventories inventories = new Inventories(outboundProducts.stream()
                    .flatMap(op -> inventoryRepository.listBy(op.getProductNo()).stream())
                    .collect(Collectors.toList()));

            outbound.allocatePicking(inventories);

        }

    }
}
