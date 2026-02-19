package com.crm.item.core.mapper;


import com.crm.item.core.entites.Item;
import com.crm.item.core.dtos.ItemDTO;
import com.crm.item.core.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ItemMapper {

    @Autowired
    private ItemRepository itemRepository;

    public ItemDTO toDTO(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .unit(item.getUnit())
                .ean(item.getEan())
                .parentItemId(item.getParentItemId())
                .build();
    }
    public Item toEntity(ItemDTO itemDTO){
        return Item.builder()
                .id(itemDTO.getId())
                .name(itemDTO.getName())
                .unit(itemDTO.getUnit())
                .ean(itemDTO.getEan())
                .active(true)
                .parentItem(itemRepository.findById(itemDTO.getParentItemId()).orElse(null))
                .build();
    }
}
