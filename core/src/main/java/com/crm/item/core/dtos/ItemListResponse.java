package com.crm.item.core.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "DTO товара(Response)")
public class ItemListResponse {
    private Integer warehouseId;
    private Integer itemId;
    private Integer quantity;
}
