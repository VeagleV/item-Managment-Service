package com.crm.item.core.repositories;

import com.crm.item.core.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByParentItem_IdAndActiveIsTrue(Integer parentItemId);

    Optional<Item> findByIdAndActiveIsTrue(Integer id);

    boolean existsByEanAndActiveIsTrue(String ean);

    List<Item> findByActiveIsTrue();
}
