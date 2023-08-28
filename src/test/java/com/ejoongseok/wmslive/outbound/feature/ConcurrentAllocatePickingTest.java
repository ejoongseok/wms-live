package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.common.ApiTest;
import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.location.domain.Inventory;
import com.ejoongseok.wmslive.location.domain.InventoryRepository;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import com.ejoongseok.wmslive.outbound.domain.Picking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.pivovarit.collectors.ParallelCollectors.parallel;
import static org.assertj.core.api.Assertions.assertThat;

class ConcurrentAllocatePickingTest extends ApiTest {

    private static final Integer parallelism = 100;
    private final ExecutorService executorService = Executors.newFixedThreadPool(parallelism);
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    OutboundRepository outboundRepository;

    @BeforeEach
    void concurrentSetUp() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request()
                .registerLPN().request()
                .registerLocation().request()
                .registerLocation().locationBarcode("TOTE-1").request()
                .registerPackagingMaterial().request()
                .assignInventory().request()
        ;
        IntStream.range(1, 11)
                .boxed()
                .collect(parallel(key -> registerOutbound(), executorService, parallelism))
                .toList();
    }

    private Integer registerOutbound() {
        Scenario.registerOutbound().request();
        return 0;
    }

    @Test
    @DisplayName("집품할당 재고 정합성 테스트")
    @Transactional
    void concurrentTest() {
        final List<Inventory> inventories = inventoryRepository.findAll();
        assertThat(inventories).hasSize(1);
        assertThat(inventories.get(0).getInventoryQuantity()).isEqualTo(1);

        LongStream.range(1, 11)
                .boxed()
                .collect(parallel(key -> allocatePicking(key), executorService, parallelism))
                .toList();
        final long sum = outboundRepository.findAll().stream()
                .flatMap(outbound -> outbound.getPickings().stream())
                .mapToLong(Picking::getQuantityRequiredForPick)
                .sum();

        System.out.println("sum = " + sum);

    }

    @Test
    @DisplayName("집품할당 재고 정합성 테스트")
    void no_concurrentTest() {
        final List<Inventory> inventories = inventoryRepository.findAll();
        assertThat(inventories).hasSize(1);
        assertThat(inventories.get(0).getInventoryQuantity()).isEqualTo(1);
        Scenario
                .allocatePickingTote().outboundNo(1L).request()
                .allocatePicking().outboundNo(1L).request();
        Scenario
                .allocatePickingTote().outboundNo(2L).request()
                .allocatePicking().outboundNo(2L).request();
    }

    private Long allocatePicking(final Long key) {
        Scenario
                .allocatePickingTote().outboundNo(key).request()
                .allocatePicking().outboundNo(key).request();
        return key;
    }
}
