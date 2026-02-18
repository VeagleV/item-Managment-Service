package com.crm.item.core.repositories;

import com.crm.item.core.entities.ItemList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemListRepository extends JpaRepository<ItemList, Integer> {

    Optional<ItemList> findByItemId(Integer itemId);

    Optional<ItemList> findById(Integer itemListId);

    Optional<ItemList> findByWarehouseId(Integer warehouseId);




}
