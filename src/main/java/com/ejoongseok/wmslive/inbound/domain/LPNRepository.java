package com.ejoongseok.wmslive.inbound.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LPNRepository extends JpaRepository<LPN, Long> {
    @Query("select l from LPN l where l.lpnBarcode = :lpnBarcode")
    Optional<LPN> findByLPNBarcode(String lpnBarcode);

    default LPN getByLPNBarcode(final String lpnBarcode) {
        return findByLPNBarcode(lpnBarcode).orElseThrow();
    }
}