package com.ejoongseok.wmslive.outbound.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OutboundRepository extends JpaRepository<Outbound, Long> {
    default Outbound getBy(final Long outboundNo) {
        return findByIdAndFetchJoin(outboundNo)
                .orElseThrow(() -> new IllegalArgumentException(
                        "출고가 존재하지 않습니다. 출고번호: %d".formatted(outboundNo)));
    }

    @Query("select o from Outbound o join fetch o.outboundProducts.outboundProducts where o.outboundNo = :outboundNo")
    Optional<Outbound> findByIdAndFetchJoin(Long outboundNo);
}
