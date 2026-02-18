package com.crm.item.core.repositories;

import com.crm.item.core.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Optional<Item> findById(Integer id);

    Optional<Item> findByParentItemId(Integer parentItemId);

    Optional<Item> findByEan(String ean);

    Optional<Item> findByIdAndActiveIsTrue(Integer id);

    boolean existsByEanAndActiveIsTrue(String ean);

    boolean existsByNameAndActiveIsTrue(String name);
}
