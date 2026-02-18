package com.crm.item.core.repositories;

import com.crm.item.core.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {


    List<Item> findByParentItemId(Integer parentItemId);

    List<Item> findByEan(String ean);

    List<Item> findByIdAndActiveIsTrue(Integer id);

    boolean existsByEanAndActiveIsTrue(String ean);

    boolean existsByNameAndActiveIsTrue(String name);
}
