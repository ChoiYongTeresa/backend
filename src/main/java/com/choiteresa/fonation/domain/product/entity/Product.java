package com.choiteresa.fonation.domain.product.entity;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product {
    // id, relation_id, expire_date, name, quantity, store_type, category, is_selected
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "relation_id")
    private FoodmarketProductRelation relation;

    @Column(nullable = false)
    private Date expireDate;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int storeType;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private int isSelected;


    public Product(String category, String name, int quantity, Date expireDate, int storeType, int weight, int isSelected) {
        this.category = category;
        this.name = name;
        this.quantity = quantity;
        this.expireDate = expireDate;
        this.storeType = storeType;
        this.weight = weight;
        this.isSelected = isSelected;
    }

}
