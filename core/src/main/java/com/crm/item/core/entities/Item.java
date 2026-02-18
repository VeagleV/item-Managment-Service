package com.crm.item.core.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.boot.cfgxml.spi.MappingReference;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ean", nullable = false, length = 13, columnDefinition = "ean13")
    private String ean;

    @Column(nullable = false)
    private Integer unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_item")
    private Item parentItem;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    public Integer getParentItemId(){ return parentItem.getId(); }
}
