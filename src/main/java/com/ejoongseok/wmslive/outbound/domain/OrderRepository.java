package com.ejoongseok.wmslive.outbound.domain;

public interface OrderRepository {
    Order getBy(Long orderNo);
}
