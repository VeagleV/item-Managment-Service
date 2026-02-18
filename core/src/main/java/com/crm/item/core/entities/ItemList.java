package com.crm.item.core.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "itemlist")
public class ItemList {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;


    @ManyToOne
    @JoinColumn(nullable = false, name = "item_id" )
    private Item item;


    @Column(nullable = false, name = "warehouse_id")
    private Integer warehouseId;

    @Column(nullable = false)
    private Integer quantity;


    public Integer getItemId() { return item.getId(); }
}
