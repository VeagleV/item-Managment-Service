package com.crm.item.core.controllers;


import com.crm.item.core.dtos.ItemDTO;
import com.crm.item.core.entites.ItemList;
import com.crm.item.core.servicies.ItemListService;
import com.crm.item.core.servicies.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@Tag(name = "работа с товарами")
public class ItemController {

    private final ItemService itemService;
    private final ItemListService itemListService;
    public ItemController(ItemService itemService, ItemListService itemListService) {
        this.itemService = itemService;
        this.itemListService = itemListService;
    }

    @GetMapping
    @Operation(summary = "Все товары")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Товар по его Id")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Integer id) {
        return itemService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Создание товара")
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO) {
        return itemService.save(itemDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "'мягкое' удаление товара")
    public ResponseEntity<ItemDTO> deleteItemById(@PathVariable Integer id) {
        return itemService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "обновление товара")
    public ResponseEntity<ItemDTO> updateItemById(@PathVariable Integer id, @RequestBody ItemDTO itemDTO) {
        return itemService.update(id, itemDTO);
    }

    @GetMapping("/itemsList/{id}")
    @Operation(summary = "itemlist по его id")
    public ResponseEntity<ItemList> getItemListById(@PathVariable Integer id) {
        return itemListService.findById(id);
    }

    @GetMapping("/warehouse/{warehouseId}")
    @Operation(summary = "все itemList по id склада(информация о том какие товары хранятся на конкретном складе)")
    public ResponseEntity<List<ItemList>> getItemListsByWarehouseId(@PathVariable Integer warehouseId){
        return itemListService.findAllByWarehouseId(warehouseId);
    }

    //TODO: Доделать парсинг файлов
    @PutMapping("/itemsList/")
    @Operation(summary = "добавление или обновление  itemList пачкой(.xlsx/.json)")
    public ResponseEntity<List<ItemList>> getItemLists() {
        return itemListService.bulkSaving();
    }

    @PutMapping("/itemsList/{warehouseId}{itemId}")
    @Operation(summary = "обновление количества товара, который хранится на конкретном складе")
    public ResponseEntity<ItemList> updateItemListQuantity(@PathVariable Integer warehouseId, @PathVariable Integer itemId, @RequestParam Integer quantity) {
        return itemListService.updateQuantity(warehouseId, itemId, quantity);
    }


}
