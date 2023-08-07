package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.inbound.domain.LPN;
import com.ejoongseok.wmslive.location.domain.Inventory;
import com.ejoongseok.wmslive.location.domain.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.ejoongseok.wmslive.inbound.domain.LPNFixture.anLPN;
import static com.ejoongseok.wmslive.location.domain.LocationFixture.aLocation;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegisterOutboundUnitTest {

    private RegisterOutbound registerOutbound;

    @BeforeEach
    void setUp() {
        registerOutbound = new RegisterOutbound(null, null, null);
    }

    @Test
    @DisplayName("주문한 상품을 출고할 수 있는 재고가 있는지 확인한다.")
    void validateInventory() {
        final Inventory inventory = new Inventory(aLocation().build(), anLPN().build());

        registerOutbound.validateInventory(List.of(inventory), 1L);
    }

    @Test
    @DisplayName("주문한 상품을 출고할 수 있는 재고가 있는지 확인한다.")
    void fail_over_quantity_validateInventory() {
        final Inventory inventory = new Inventory(aLocation().build(), anLPN().build());

        assertThatThrownBy(() -> {
            registerOutbound.validateInventory(List.of(inventory), 2L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }

    @Test
    @DisplayName("주문한 상품을 출고할 수 있는 재고가 있는지 확인한다.")
    void expire_validateInventory() {
        final Location location = aLocation().build();
        final LPN expiredLPN = anLPN().expirationAt(LocalDateTime.now().minusDays(1)).build();
        final Inventory inventory = new Inventory(location, expiredLPN);

        assertThatThrownBy(() -> {
            registerOutbound.validateInventory(List.of(inventory), 1L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }
}