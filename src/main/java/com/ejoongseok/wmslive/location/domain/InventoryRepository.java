package com.ejoongseok.wmslive.location.domain;

import com.ejoongseok.wmslive.outbound.domain.Inventories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT i FROM Inventory i WHERE i.productNo = :productNo")
    List<Inventory> listBy(Long productNo);

    default Inventories inventoriesBy(final Long productNo) {
        return new Inventories(listBy(productNo));
    }

    @Query("SELECT i FROM Inventory i WHERE i.productNo IN :productNos")
    List<Inventory> listBy(Set<Long> productNos);

    default Inventories inventoriesBy(final Set<Long> productNos) {
        return new Inventories(listBy(productNos));
    }
}