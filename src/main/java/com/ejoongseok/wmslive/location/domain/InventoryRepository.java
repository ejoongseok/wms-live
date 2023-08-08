package com.ejoongseok.wmslive.location.domain;

import com.ejoongseok.wmslive.outbound.domain.Inventories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT i FROM Inventory i WHERE i.productNo = :productNo")
    List<Inventory> findByProductNo(Long productNo);

    default Inventories inventoriesBy(final Long productNo) {
        return new Inventories(
                findByProductNo(productNo)
        );
    }
}