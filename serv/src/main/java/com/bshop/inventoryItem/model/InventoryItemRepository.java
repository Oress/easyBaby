package com.bshop.inventoryItem.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InventoryItemRepository extends PagingAndSortingRepository<InventoryItem, Integer> {
    @Query("select ii from INVITEM ii where ii.product.id = ?1")
    List<InventoryItem> findByProductId(Integer id);

    @Query("select ii from INVITEM ii where ii.status = 'AVAILABLE' and ii.product.id = ?1")
    List<InventoryItem> findByProductId(Integer id,  Pageable pageable);
}
