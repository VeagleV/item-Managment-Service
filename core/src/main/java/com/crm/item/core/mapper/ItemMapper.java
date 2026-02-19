package com.crm.item.core.mapper;


import com.crm.item.core.dtos.ItemRequest;
import com.crm.item.core.dtos.ItemResponse;
import com.crm.item.core.entities.Item;
import org.springframework.stereotype.Component;


@Component
public class ItemMapper {

    public ItemResponse toItemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .ean(item.getEan())
                .parentItemId(item.getParentItemId())
                .unit(item.getUnit())
                .build();
    }

    public Item toItem(ItemRequest itemRequest, Item parentItem) {
        return Item.builder()
                .name(itemRequest.getName())
                .ean(itemRequest.getEan())
                .unit(itemRequest.getUnit())
                .parentItem(parentItem)
                .build();
    }

}
