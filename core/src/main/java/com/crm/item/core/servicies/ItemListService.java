package com.crm.item.core.servicies;

import com.crm.item.core.entities.ItemList;
import com.crm.item.core.repositories.ItemListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public void save(ItemList itemList) {
        itemListRepository.save(itemList);
    }

    public ResponseEntity<ItemList> findById(Integer id) {
        ItemList itemList = itemListRepository.findById(id).orElse(null);
        if (itemList == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    public ResponseEntity<List<ItemList>> findAllByWarehouseId(Integer warehouseId) {
        List<ItemList> itemsList = itemListRepository.findByWarehouseId(warehouseId);
        return itemsList.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(itemsList, HttpStatus.OK);
    }

    public ResponseEntity<List<ItemList>> findAllByItemId(Integer itemId) {
        List<ItemList> itemsList = itemListRepository.findByItem_Id(itemId);
        return itemsList.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(itemsList, HttpStatus.OK);
    }

    public ResponseEntity<ItemList> findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId) {
        ItemList itemList = itemListRepository.findByWarehouseIdAndItem_Id(warehouseId, itemId).orElse(null);
        if (itemList == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    public ResponseEntity<ItemList> updateQuantity(Integer warehouseId, Integer itemId, Integer newQuantity) {
        ItemList itemList = itemListRepository.findByWarehouseIdAndItem_Id(warehouseId, itemId).orElse(null);
        if (itemList == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        itemList.setQuantity(newQuantity);
        itemListRepository.save(itemList);
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    //TODO: реализовать сохранение / обновление товаров с помощью JSON/XLSX файлов
    public ResponseEntity<List<ItemList>> bulkSaving(){

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }


}
