package com.crm.item.core.mapper;

import com.crm.item.core.dtos.ItemListResponse;
import com.crm.item.core.entities.ItemList;
import org.springframework.stereotype.Component;

@Component
public class ItemListMapper {

    public ItemListResponse toItemListResponce(ItemList itemList) {
        return ItemListResponse.builder()
                .itemId(itemList.getItemId())
                .warehouseId(itemList.getWarehouseId())
                .quantity(itemList.getQuantity())
                .build();
    }
}
