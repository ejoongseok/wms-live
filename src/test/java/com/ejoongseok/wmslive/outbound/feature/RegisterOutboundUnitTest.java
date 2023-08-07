package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.inbound.domain.LPN;
import com.ejoongseok.wmslive.location.domain.Inventory;
import com.ejoongseok.wmslive.location.domain.Location;
import com.ejoongseok.wmslive.outbound.domain.Order;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ejoongseok.wmslive.inbound.domain.LPNFixture.anLPN;
import static com.ejoongseok.wmslive.location.domain.LocationFixture.aLocation;
import static com.ejoongseok.wmslive.outbound.domain.OrderFixture.anOrder;
import static com.ejoongseok.wmslive.outbound.domain.OrderProductFixture.anOrderProduct;
import static com.ejoongseok.wmslive.outbound.feature.PackagingMaterialFIxture.aPackagingMaterial;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegisterOutboundUnitTest {

    private RegisterOutbound registerOutbound;

    @BeforeEach
    void setUp() {
        registerOutbound = new RegisterOutbound(null, null, null, null);
    }

    @Test
    @DisplayName("주문한 상품을 포장할 수 있는 포장재를 찾는다.")
    void findOptimalPackagingMaterial() {
        final Order order = anOrder().build();
        final PackagingMaterial packagingMaterial = aPackagingMaterial().build();

        final Optional<PackagingMaterial> optimalPackagingMaterial = registerOutbound.findOptimalPackagingMaterial(order, List.of(packagingMaterial));

        assertThat(optimalPackagingMaterial.isPresent()).isEqualTo(true);
    }

    @Test
    @DisplayName("주문한 상품을 포장할 수 있는 포장재를 찾는다.")
    void empty_findOptimalPackagingMaterial() {
        final Order order = anOrder()
                .orderProduct(anOrderProduct().orderQuantity(100L))
                .build();
        final PackagingMaterial packagingMaterial = aPackagingMaterial().build();

        final Optional<PackagingMaterial> optimalPackagingMaterial = registerOutbound.findOptimalPackagingMaterial(order, List.of(packagingMaterial));

        assertThat(optimalPackagingMaterial.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("주문한 상품을 출고할 수 있는 재고가 있는지 확인한다.")
    void validateInventory() {
        final Inventory inventory = new Inventory(aLocation().build(), anLPN().build());

        new Inventories(List.of(inventory), 1L).validateInventory();
    }

    @Test
    @DisplayName("주문한 상품을 출고할 수 있는 재고가 있는지 확인한다.")
    void fail_over_quantity_validateInventory() {
        final Inventory inventory = new Inventory(aLocation().build(), anLPN().build());

        assertThatThrownBy(() -> {
            new Inventories(List.of(inventory), 2L).validateInventory();
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
            new Inventories(List.of(inventory), 1L).validateInventory();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }
}