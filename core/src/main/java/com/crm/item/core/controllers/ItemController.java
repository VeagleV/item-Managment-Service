package com.crm.item.core.controllers;

import com.crm.item.core.dtos.ItemListResponse;
import com.crm.item.core.dtos.ItemRequest;
import com.crm.item.core.dtos.ItemResponse;
import com.crm.item.core.entities.ItemList;
import com.crm.item.core.services.ItemListService;
import com.crm.item.core.services.ItemService;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@Tag(name = "работа с товарами")
@Validated
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
        List<ItemResponse> ItemResponseList = itemService.findAll();
        return new ResponseEntity<>(ItemResponseList, HttpStatus.OK);
    }

    @Operation(summary = "Товар по его Id")
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable @PositiveOrZero Integer id) {
        ItemResponse itemResponse =  itemService.findById(id);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @Operation(summary = "Все дочерние товары по id предка")
    @GetMapping("/parentItemId/{parentItemId}")
    public ResponseEntity<List<ItemResponse>> getItemsByParentItemId(@PathVariable @PositiveOrZero Integer parentItemId) {
        List<ItemResponse> itemResponseList = itemService.findAllByParentItemId(parentItemId);
        return new ResponseEntity<>(itemResponseList, HttpStatus.OK);
    }

    @Operation(summary = "Создание товара")
    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody @Valid ItemRequest itemRequest) {
        ItemResponse itemResponse = itemService.save(itemRequest);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }


    @Operation(summary = "'мягкое' удаление товара")
    @DeleteMapping("/{id}")
    public ResponseEntity<ItemResponse> deleteItemById(@PathVariable @PositiveOrZero Integer id) {
       itemService.delete(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIXME: Не работает. выкидывает 415 Unsupported Media Type
    @Operation(summary = "обновление товара")
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItemById(@PathVariable @PositiveOrZero Integer id, @RequestBody @Valid ItemRequest itemRequest) {
        itemService.update(id, itemRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "itemlist по его id")
    @GetMapping("/itemsList/{id}")
    public ResponseEntity<ItemList> getItemListById(@PathVariable Integer id) {
        ItemList itemList = itemListService.findById(id);
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @PostMapping("/itemsList/items")
    public ResponseEntity<List<ItemListResponse>> getAllItemList(@RequestBody @JsonProperty("item_id") List<Integer> itemIdList) {
        List<ItemListResponse> itemListResponse = itemListService.findAllByItemIdList(itemIdList);
        return new ResponseEntity<>(itemListResponse, HttpStatus.OK);
    }

    @Operation(summary = "все itemList по id склада(информация о том какие товары хранятся на конкретном складе)")
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<ItemList>> getItemListsByWarehouseId(@PathVariable @PositiveOrZero Integer warehouseId){
        List<ItemList> itemLists = itemListService.findAllByWarehouseId(warehouseId);
        return new ResponseEntity<>(itemLists, HttpStatus.OK);
    }

    //TODO: Доделать парсинг файлов
    @Operation(summary = "добавление или обновление  itemList пачкой(.xlsx/.json)")
    @PutMapping("/itemsList/")
    public ResponseEntity<List<ItemList>> getItemLists() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(summary = "обновление количества товара, который хранится на конкретном складе")
    @PutMapping("/itemsList/{warehouseId}/{itemId}")
    public ResponseEntity<ItemList> updateItemListQuantity(@PathVariable @PositiveOrZero Integer warehouseId,
                                                           @PathVariable @PositiveOrZero Integer itemId,
                                                           @RequestParam @PositiveOrZero Integer quantity) {
        itemListService.updateQuantity(warehouseId, itemId, quantity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
