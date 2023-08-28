package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.location.RetryOnOptimisticLockingFailure;
import com.ejoongseok.wmslive.location.domain.InventoryRepository;
import com.ejoongseok.wmslive.outbound.domain.Inventories;
import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import com.ejoongseok.wmslive.outbound.domain.PickingAllocator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AllocatePicking {
    private final PickingAllocator pickingAllocator = new PickingAllocator();
    private final OutboundRepository outboundRepository;
    private final InventoryRepository inventoryRepository;

    @PostMapping("/outbounds/{outboundNo}/allocate-picking")
    @Transactional
    @RetryOnOptimisticLockingFailure
    public void request(@PathVariable final Long outboundNo) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
        final Inventories inventories = inventoryRepository.inventoriesBy(outbound.getProductNos());

        pickingAllocator.allocatePicking(outbound, inventories);
    }


}
