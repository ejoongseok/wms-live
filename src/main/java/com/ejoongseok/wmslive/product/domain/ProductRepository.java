package com.ejoongseok.wmslive.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    default Product getBy(final Long productNo) {
        return findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException(
                        "상품이 존재하지 않습니다. %d".formatted(productNo)));
    }
}
