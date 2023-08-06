package com.ejoongseok.wmslive.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterPackagingMaterialTest {

    private RegisterPackagingMaterial registerPackagingMaterial;

    @BeforeEach
    void setUp() {
        registerPackagingMaterial = new RegisterPackagingMaterial();
    }

    @Test
    @DisplayName("포장재를 등록한다.")
    void registerPackagingMaterial() {
        final RegisterPackagingMaterial.Request request = new RegisterPackagingMaterial.Request();
        registerPackagingMaterial.request(request);
    }

    private class RegisterPackagingMaterial {
        public void request(final Request request) {

        }

        public record Request() {
        }
    }
}
