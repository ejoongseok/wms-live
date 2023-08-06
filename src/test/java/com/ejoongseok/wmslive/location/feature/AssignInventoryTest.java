package com.ejoongseok.wmslive.location.feature;

import com.ejoongseok.wmslive.common.ApiTest;
import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.location.domain.Inventory;
import com.ejoongseok.wmslive.location.domain.Location;
import com.ejoongseok.wmslive.location.domain.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AssignInventoryTest extends ApiTest {

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void setUpAssignInventory() {
        Scenario
                .registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request()
                .registerLPN().request()
                .registerLocation().request();
    }

    @Test
    @DisplayName("로케이션에 재고를 할당한다.")
    @Transactional
    void assignInventory() {
        Scenario
                .assignInventory().request();

        assertAssignInventory();
    }

    private void assertAssignInventory() {
        final String locationBarcode = "A-1-1";
        final Location location = locationRepository.getByLocationBarcode(locationBarcode);
        final List<Inventory> inventories = location.getInventories();
        final Inventory inventory = inventories.get(0);
        assertThat(inventories).hasSize(1);
        assertThat(inventory.getInventoryQuantity()).isEqualTo(1L);
    }

}
