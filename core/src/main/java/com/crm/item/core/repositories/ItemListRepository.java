package com.crm.item.core.repositories;

import com.crm.item.core.entities.ItemList;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ItemListRepository extends JpaRepository<ItemList, Integer> {

    List<ItemList> findByItem_Id(Integer itemId);

    List<ItemList> findByWarehouseId(Integer warehouseId);

    Optional<ItemList> findByWarehouseIdAndItem_Id(Integer warehouseId, Integer itemId);

    List<ItemList> findAllByItem_IdIn(Collection<Integer> itemIds);

}
