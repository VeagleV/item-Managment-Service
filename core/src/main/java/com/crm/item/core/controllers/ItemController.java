package com.crm.item.core.controllers;

import com.crm.item.core.dtos.ItemRequest;
import com.crm.item.core.dtos.ItemResponse;
import com.crm.item.core.entites.ItemList;
import com.crm.item.core.servicies.ItemListService;
import com.crm.item.core.servicies.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Товар по его Id")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Integer id) {
        return itemService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Создание товара")
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        return itemService.save(itemRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "'мягкое' удаление товара")
    public ResponseEntity<ItemResponse> deleteItemById(@PathVariable Integer id) {
        return itemService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "обновление товара")
    public ResponseEntity<ItemResponse> updateItemById(@PathVariable Integer id, @RequestBody ItemRequest itemRequest) {
        return itemService.update(id, itemRequest);
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
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("/itemsList/{warehouseId}/{itemId}")
    @Operation(summary = "обновление количества товара, который хранится на конкретном складе")
    public ResponseEntity<ItemList> updateItemListQuantity(@PathVariable Integer warehouseId, @PathVariable Integer itemId, @RequestParam Integer quantity) {
        return itemListService.updateQuantity(warehouseId, itemId, quantity);
    }


}
