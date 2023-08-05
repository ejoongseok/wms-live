package com.ejoongseok.wmslive.location.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("select l from Location l where l.locationBarcode = :locationBarcode")
    Optional<Location> findByLocationBarcode(String locationBarcode);
}
