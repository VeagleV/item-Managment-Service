package com.crm.item.core.controllers;

import com.crm.item.core.dtos.ItemRequest;
import com.crm.item.core.dtos.ItemResponse;
import com.crm.item.core.entites.ItemList;
import com.crm.item.core.services.ItemListService;
import com.crm.item.core.services.ItemService;
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

    @Operation(summary = "Все товары")
    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return itemService.findAll();
    }

    @Operation(summary = "Товар по его Id")
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Integer id) {
        return itemService.findById(id);
    }

    @Operation(summary = "Все дочерние товары по id предка")
    @GetMapping("/parentItemId/{parentItemId}")
    public ResponseEntity<List<ItemResponse>> getItemsByParentItemId(@PathVariable Integer parentItemId) {
        return itemService.findAllByParentItemId(parentItemId);
    }

    @Operation(summary = "Создание товара")
    @PostMapping("/")
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        return itemService.save(itemRequest);
    }


    @Operation(summary = "'мягкое' удаление товара")
    @DeleteMapping("/{id}")
    public ResponseEntity<ItemResponse> deleteItemById(@PathVariable Integer id) {
        return itemService.delete(id);
    }

    //FIXME: Не работает. выкидывает 415 Unsupported Media Type
    @Operation(summary = "обновление товара")
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItemById(@PathVariable Integer id, @RequestBody ItemRequest itemRequest) {
        return itemService.update(id, itemRequest);
    }

    @Operation(summary = "itemlist по его id")
    @GetMapping("/itemsList/{id}")
    public ResponseEntity<ItemList> getItemListById(@PathVariable Integer id) {
        return itemListService.findById(id);
    }

    @Operation(summary = "все itemList по id склада(информация о том какие товары хранятся на конкретном складе)")
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<ItemList>> getItemListsByWarehouseId(@PathVariable Integer warehouseId){
        return itemListService.findAllByWarehouseId(warehouseId);
    }

    //TODO: Доделать парсинг файлов
    @Operation(summary = "добавление или обновление  itemList пачкой(.xlsx/.json)")
    @PutMapping("/itemsList/")
    public ResponseEntity<List<ItemList>> getItemLists() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(summary = "обновление количества товара, который хранится на конкретном складе")
    @PutMapping("/itemsList/{warehouseId}/{itemId}")
    public ResponseEntity<ItemList> updateItemListQuantity(@PathVariable Integer warehouseId, @PathVariable Integer itemId, @RequestParam Integer quantity) {
        return itemListService.updateQuantity(warehouseId, itemId, quantity);
    }


}
