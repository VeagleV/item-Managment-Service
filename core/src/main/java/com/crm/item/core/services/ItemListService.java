package com.crm.item.core.services;

import com.crm.item.core.entities.ItemList;
import com.crm.item.core.exceptions.ResourceNotFoundException;
import com.crm.item.core.repositories.ItemListRepository;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemListService {
    private final ItemListRepository itemListRepository;

    @Autowired
    public ItemListService(ItemListRepository itemListRepository) {
        this.itemListRepository = itemListRepository;
    }


    public ItemList save(ItemList itemList) {
        itemListRepository.save(itemList);
        return itemList;
    }

    public ItemList findById(Integer id) {
        return itemListRepository.findById(id).orElse(null);
    }

    public List<ItemList> findAllByWarehouseId(Integer warehouseId) {
        return itemListRepository.findByWarehouseId(warehouseId);
    }

    public List<ItemList> findAllByItemId(Integer itemId) {
        return itemListRepository.findByItem_Id(itemId);
    }

    public ItemList findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId) {
        return itemListRepository.findByWarehouseIdAndItem_Id(warehouseId, itemId).orElse(null);
    }

    public void updateQuantity(Integer warehouseId, Integer itemId,@PositiveOrZero Integer newQuantity) {
        ItemList itemList = itemListRepository.findByWarehouseIdAndItem_Id(warehouseId, itemId).orElse(null);
        if (itemList == null) throw new ResourceNotFoundException("ItemList");
        itemList.setQuantity(newQuantity);
        save(itemList);
    }

    //TODO: реализовать сохранение / обновление товаров с помощью JSON/XLSX файлов
    public List<ItemList> bulkSaving(){
        List<ItemList> itemLists = new ArrayList<>();

        return itemLists;
    }


}
