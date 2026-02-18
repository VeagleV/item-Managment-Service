package com.crm.item.core.repositories;

import com.crm.item.core.entities.ItemList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemListRepository extends JpaRepository<ItemList, Integer> {

    List<ItemList> findByItem_Id(Integer itemId);

    List<ItemList> findByWarehouseId(Integer warehouseId);




}
