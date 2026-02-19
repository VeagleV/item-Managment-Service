package com.crm.item.core.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "DTO товара(Request)")
public class ItemRequest {

    @JsonProperty
    @NotBlank
    private String name;

    @JsonProperty
    @NotBlank
    private String ean;

    @JsonProperty("parent_item_Id")
    private Integer parentItemId;

    @JsonProperty
    @PositiveOrZero
    private Integer unit;



}
