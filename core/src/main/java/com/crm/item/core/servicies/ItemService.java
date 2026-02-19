package com.crm.item.core.servicies;


import com.crm.item.core.dtos.ItemDTO;
import com.crm.item.core.entities.Item;
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

    private final ItemMapper itemMapper;
    @Autowired
    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public ResponseEntity<ItemDTO> save(ItemDTO itemDTO) {
        itemRepository.save(itemMapper.toEntity(itemDTO));
        return new ResponseEntity<>(itemDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<ItemDTO> findById(Integer id) {
        Item item = itemRepository.findById(id).orElse(null);
        return item == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(itemMapper.toDTO(item), HttpStatus.OK);
    }

    public ResponseEntity<List<ItemDTO>> findAll() {
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemDTOs = items.stream()
                .map(itemMapper::toDTO)
                .toList();
        return items.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(itemDTOs, HttpStatus.OK);
    }

    public ResponseEntity<ItemDTO> update(Integer id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id).orElse(null);
        if ( item == null )  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        itemRepository.save(itemMapper.toEntity(itemDTO));
        return new ResponseEntity<>(itemDTO, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ItemDTO> delete(Integer id) {
        Item item = itemRepository.findById(id).orElse(null);
        if ( item == null )  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        item.setActive(false);
        itemRepository.save(item);
        return new ResponseEntity<>(itemMapper.toDTO(item), HttpStatus.NO_CONTENT);
    }
}
