package com.choiteresa.fonation.domain.product.entity;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
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
    private FoodmarketProductRelation relationId;

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
    private boolean isSelected;

    @ManyToOne
    @JoinColumn(name = "donation_form_id")
    private ProductDonationForm donationFormId;
    // 필요한 생성자 추가
    public Product(String category, String name, int quantity, Date expireDate, int storeType, int weight, boolean isSelected) {
        this.category = category;
        this.name = name;
        this.quantity = quantity;
        this.expireDate = expireDate;
        this.storeType = storeType;
        this.weight = weight;
        this.isSelected = isSelected;
    }

    public Product() {

    }

    public void setDonationForm(ProductDonationForm form) {
        this.donationFormId = form;
    }

//    public Product(String category, String name, Date expireDate, )
}
