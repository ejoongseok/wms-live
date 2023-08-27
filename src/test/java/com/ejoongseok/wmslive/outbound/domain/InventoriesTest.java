package com.ejoongseok.wmslive.outbound.domain;

import com.ejoongseok.wmslive.location.domain.Inventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.ejoongseok.wmslive.inbound.domain.LPNFixture.anLPN;
import static com.ejoongseok.wmslive.location.domain.InventoriesFixture.anInventories;
import static com.ejoongseok.wmslive.location.domain.InventoryFixture.anInventory;
import static com.ejoongseok.wmslive.location.domain.LocationFixture.aLocation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InventoriesTest {

    @Test
    @DisplayName("출고를 위한 효율적인 재고 목록을 생성한다.")
    void makeEfficientInventoriesForPicking() {
        final Inventories inventories = createInventories();

        final Inventories result = inventories.makeEfficientInventoriesForPicking(1L, 3L);

        assertEfficientForPicking(result);
    }

    private Inventories createInventories() {
        final LocalDateTime now = LocalDateTime.now();
        return anInventories()
                .inventories(
                        anInventory()
                                .inventoryQuantity(3L)
                                .lpn(anLPN().expirationAt(now.plusDays(3L)))
                                .location(aLocation().locationBarcode("A1"))
                        ,
                        anInventory()
                                .inventoryQuantity(2L)
                                .lpn(anLPN().expirationAt(now.plusDays(1L)))
                                .location(aLocation().locationBarcode("A2"))
                        ,
                        anInventory()
                                .inventoryQuantity(3L)
                                .lpn(anLPN().expirationAt(now.plusDays(1L)))
                                .location(aLocation().locationBarcode("A3"))
                        ,
                        anInventory()
                                .inventoryQuantity(3L)
                                .lpn(anLPN().expirationAt(now.plusDays(1L)))
                                .location(aLocation().locationBarcode("A1-1"))
                        ,
                        anInventory().lpn(anLPN().expirationAt(now.minusDays(1L))),
                        anInventory().inventoryQuantity(0L)
                )
                .build();
    }

    private void assertEfficientForPicking(final Inventories result) {
        final List<Inventory> resultList = result.toList();
        assertThat(resultList).hasSize(4);
        assertThat(resultList.get(0).getLocationBarcode()).isEqualTo("A1-1");
        assertThat(resultList.get(1).getLocationBarcode()).isEqualTo("A3");
        assertThat(resultList.get(2).getLocationBarcode()).isEqualTo("A2");
        assertThat(resultList.get(3).getLocationBarcode()).isEqualTo("A1");
    }

    @Test
    @DisplayName("출고해야할 수량보다 출고가능한 수량이 적을 경우 예외가 발생한다.")
    void fail_no_availablity_makeEfficientInventoriesForPicking() {
        final Inventories inventories = anInventories()
                .inventories(
                        anInventory(),
                        anInventory(),
                        anInventory().lpn(anLPN().expirationAt(LocalDateTime.now().minusDays(1L))),
                        anInventory().inventoryQuantity(0L),
                        anInventory().inventoryQuantity(0L)
                )
                .build();

        assertThatThrownBy(() -> {
            inventories.makeEfficientInventoriesForPicking(1L, 3L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }
}