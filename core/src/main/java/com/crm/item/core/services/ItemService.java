package com.crm.item.core.services;

import com.crm.item.core.dtos.ItemRequest;
import com.crm.item.core.dtos.ItemResponse;
import com.crm.item.core.entites.Item;
import com.crm.item.core.handlers.EanHandler;
import com.crm.item.core.mapper.ItemMapper;
import com.crm.item.core.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final EanHandler eanHandler;

    private final ItemMapper itemMapper;
    @Autowired
    public ItemService(ItemRepository itemRepository, EanHandler eanHandler, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.eanHandler = eanHandler;
        this.itemMapper = itemMapper;
    }

//    public ResponseEntity<Object> validateEanInRequest(ItemRequest ItemRequest) {
//        if (ItemRequest.getEan() == null || ItemRequest.getEan().isEmpty()) {
//            ItemRequest.setEan(eanHandler.generateEan(13));
//        } else {
//            if (!eanHandler.isValidEan(ItemRequest.getEan())) {
//                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_CONTENT);
//            }
//            if (itemRepository.existsByEanAndActiveIsTrue(ItemRequest.getEan())) {
//                return new ResponseEntity<>(HttpStatus.CONFLICT);
//            }
//        }
//        return null;
//    }

    public ResponseEntity<ItemResponse> save(ItemRequest ItemRequest) {
        String ean = ItemRequest.getEan();
        if (ean == null || ean.isEmpty()) {
            String newEan = eanHandler.generateEan(13);
            while (itemRepository.existsByEanAndActiveIsTrue(newEan)){
                newEan = eanHandler.generateEan(13);
            }
        } else {
            if (!eanHandler.isValidEan(ean)) {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_CONTENT);
            }
            if (itemRepository.existsByEanAndActiveIsTrue(ean)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        Integer parentItemID = ItemRequest.getParentItemId();
        Item parentItem = parentItemID == null ? null : itemRepository.findById(parentItemID).orElse(null);
        Item savedItem = itemRepository.save(itemMapper.toItem(ItemRequest, parentItem));
        return new ResponseEntity<>(itemMapper.toItemResponse(savedItem), HttpStatus.CREATED);
    }

    public ResponseEntity<ItemResponse> findById(Integer id) {
        Item item = itemRepository.findByIdAndActiveIsTrue(id).orElse(null);
        return item == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(itemMapper.toItemResponse(item), HttpStatus.OK);
    }

    public ResponseEntity<List<ItemResponse>> findAll() {
        List<Item> items = itemRepository.findByActiveIsTrue();
        List<ItemResponse> itemResponseList = items.stream()
                .map(itemMapper::toItemResponse)
                .toList();
        return new ResponseEntity<>(itemResponseList, HttpStatus.OK);
    }

    public ResponseEntity<List<ItemResponse>> findByAllByParentItemId(Integer parentItemId) {
        List<Item> items = itemRepository.findByParentItem_IdAndActiveIsTrue(parentItemId);
        List<ItemResponse> itemResponseList = items.stream()
                .map(itemMapper::toItemResponse)
                .toList();
        return new ResponseEntity<>(itemResponseList, HttpStatus.OK);
    }

    public ResponseEntity<ItemResponse> update(Integer id, ItemRequest itemRequest) {
        Item item = itemRepository.findByIdAndActiveIsTrue(id).orElse(null);
        if ( item == null )  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        String newEan = itemRequest.getEan();
        if (newEan == null || newEan.isEmpty() || !eanHandler.isValidEan(newEan)) new ResponseEntity<>(HttpStatus.UNPROCESSABLE_CONTENT);

        if (!newEan.equals(itemRequest.getEan())) {
            if (itemRepository.existsByEanAndActiveIsTrue(newEan)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            item.setEan(newEan);
        }
        Integer parentItemID = itemRequest.getParentItemId();
        Item parentItem = parentItemID == null ? null : itemRepository.findById(parentItemID).orElse(null);
        item.setName(itemRequest.getName());
        item.setUnit(itemRequest.getUnit());
        item.setParentItem(parentItem);
        itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ItemResponse> delete(Integer id) {
        Item item = itemRepository.findByIdAndActiveIsTrue(id).orElse(null);
        if ( item == null )  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        item.setActive(false);
        itemRepository.save(item);
        return new ResponseEntity<>(itemMapper.toItemResponse(item), HttpStatus.NO_CONTENT);
    }
}
