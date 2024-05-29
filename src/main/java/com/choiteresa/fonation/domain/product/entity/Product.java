package com.choiteresa.fonation.domain.product.entity;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    // id, relation_id, expire_date, name, quantity, store_type, category, is_selected
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "relation_id")
    private FoodmarketProductRelation relation;

    private Date expireDate;

    private String name;

    private int quantity;

    private int storeType;

    private String category;

    private int weight;

    private boolean isSelected;
}
