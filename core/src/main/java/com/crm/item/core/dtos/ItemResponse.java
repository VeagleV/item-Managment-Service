package com.crm.item.core.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "DTO товара(Response)")
public class ItemResponse {

    @JsonProperty
    @NotNull
    private Integer id;

    @JsonProperty
    @NotBlank
    private String name;

    @JsonProperty
    @NotBlank
    private String ean;

    @JsonProperty("parent_item_id")
    private Integer parentItemId;

    @JsonProperty
    @PositiveOrZero
    private Integer unit;
}
