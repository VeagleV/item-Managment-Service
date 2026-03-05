package com.crm.item.core.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("warehouse_id")
    private Integer warehouseId;
    @JsonProperty("item_id")
    private Integer itemId;
    @JsonProperty("quantity")
    private Integer quantity;
}
