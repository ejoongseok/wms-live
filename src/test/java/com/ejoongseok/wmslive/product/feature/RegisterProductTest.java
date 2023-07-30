package com.ejoongseok.wmslive.product.feature;

import com.ejoongseok.wmslive.common.ApiTest;
import com.ejoongseok.wmslive.common.Scenario;
import com.ejoongseok.wmslive.product.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterProductTest extends ApiTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품을 등록한다.")
    void registerProduct() {
        Scenario.registerProduct().request();

        assertThat(productRepository.findAll()).hasSize(1);
    }

}
