package com.crm.item.core.services;

import com.crm.item.core.dtos.ItemRequest;
import com.crm.item.core.dtos.ItemResponse;
import com.crm.item.core.entities.Item;
import com.crm.item.core.exceptions.DuplicateEanException;
import com.crm.item.core.exceptions.InvalidEanException;
import com.crm.item.core.exceptions.ResourceNotFoundException;
import com.crm.item.core.handlers.EanHandler;
import com.crm.item.core.mapper.ItemMapper;
import com.crm.item.core.repositories.ItemRepository;
import org.profiling.Profiling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profiling
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

    public ItemResponse save(ItemRequest itemRequest) {
        handleEan(itemRequest);
        Integer parentItemId = itemRequest.getParentItemId();
        Item parentItem = handleParentId(parentItemId);
        Item savedItem = itemRepository.save(itemMapper.toItem(itemRequest, parentItem));
        return itemMapper.toItemResponse(savedItem);
    }

    public ItemResponse findById(Integer id) {
        Item item = itemRepository.findByIdAndActiveIsTrue(id).orElse(null);
        if (item == null) throw new ResourceNotFoundException("There is no item with ID = " + id);
        return itemMapper.toItemResponse(item);
    }

    public List<ItemResponse> findAll() {
        List<Item> items = itemRepository.findByActiveIsTrue();
         return items.stream()
                .map(itemMapper::toItemResponse)
                .toList();
    }

    public List<ItemResponse> findAllByParentItemId(Integer parentItemId) {
        List<Item> items = itemRepository.findByParentItem_IdAndActiveIsTrue(parentItemId);
        return items.stream()
                .map(itemMapper::toItemResponse)
                .toList();
    }

    public void update(Integer id, ItemRequest itemRequest) {
        Item item = itemRepository.findByIdAndActiveIsTrue(id).orElse(null);
        if ( item == null )  throw new ResourceNotFoundException("There is no item with ID = " + id);
        String newEan = itemRequest.getEan();
        if (newEan == null || newEan.isEmpty() || !eanHandler.isValidEan(newEan)) throw new InvalidEanException("EAN is not valid");
        if (!newEan.equals(item.getEan())) {
            if (itemRepository.existsByEanAndActiveIsTrue(newEan))  throw new DuplicateEanException("Ean is already in use");
            item.setEan(newEan);
        }
        Integer parentItemId = itemRequest.getParentItemId();
        Item parentItem = handleParentId(parentItemId);
        item.setName(itemRequest.getName());
        item.setUnit(itemRequest.getUnit());
        item.setParentItem(parentItem);
        itemRepository.save(item);
    }

    public void  delete(Integer id) {
        Item item = itemRepository.findByIdAndActiveIsTrue(id).orElse(null);
        if ( item == null )  throw new ResourceNotFoundException("There is no item with ID = " + id);
        item.setActive(false);
        itemRepository.save(item);
    }

    private String generateUniqueEan(){
        String newEan = eanHandler.generateEan(13);
        while (itemRepository.existsByEanAndActiveIsTrue(newEan)){
            newEan = eanHandler.generateEan(13);
        }
        return newEan;
    }

    private void handleEan(ItemRequest itemRequest){
        String ean = itemRequest.getEan();
        if (ean == null || ean.isEmpty()) {
            itemRequest.setEan(generateUniqueEan());
        } else {
            if (!eanHandler.isValidEan(ean)) {
                throw new InvalidEanException("Ean is not valid");
            }
            if (itemRepository.existsByEanAndActiveIsTrue(ean)) {
                throw new DuplicateEanException("Ean is already in use");
            }
        }
    }

    private Item handleParentId(Integer parentItemId){
        Item parentItem = null;
        if (parentItemId != null) {
            parentItem = itemRepository.findById(parentItemId).orElse(null);
            if (parentItem == null) throw new ResourceNotFoundException("There is no parent item with ID = " + parentItemId);
        }
        return parentItem;
    }
}
