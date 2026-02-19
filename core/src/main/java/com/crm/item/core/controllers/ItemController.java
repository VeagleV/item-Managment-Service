package com.crm.item.core.controllers;


import com.crm.item.core.dtos.ItemDTO;
import com.crm.item.core.entities.ItemList;
import com.crm.item.core.servicies.ItemListService;
import com.crm.item.core.servicies.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemListService itemListService;
    public ItemController(ItemService itemService, ItemListService itemListService) {
        this.itemService = itemService;
        this.itemListService = itemListService;
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Integer id) {
        return itemService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO) {
        return itemService.save(itemDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItemDTO> deleteItemById(@PathVariable Integer id) {
        return itemService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItemById(@PathVariable Integer id, @RequestBody ItemDTO itemDTO) {
        return itemService.update(id, itemDTO);
    }

    @GetMapping("/itemsList/{id}")
    public ResponseEntity<ItemList> getItemListById(@PathVariable Integer id) {
        return itemListService.findById(id);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<ItemList>> getItemListsByWarehouseId(@PathVariable Integer warehouseId){
        return itemListService.findAllByWarehouseId(warehouseId);
    }

    //TODO: Доделать парсинг файлов
    @PutMapping("/itemsList/")
    public ResponseEntity<List<ItemList>> getItemLists() {
        return itemListService.bulkSaving();
    }

    @PutMapping("/itemsList/{warehouseId}{itemId}")
    public ResponseEntity<ItemList> getItemList(@PathVariable Integer warehouseId, @PathVariable Integer itemId, @RequestParam Integer quantity) {
        return itemListService.updateQuantity(warehouseId, itemId, quantity);
    }


}
