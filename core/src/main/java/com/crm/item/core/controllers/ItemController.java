package com.crm.item.core.controllers;


import com.crm.item.core.dtos.ItemDTO;
import com.crm.item.core.servicies.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
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


}
