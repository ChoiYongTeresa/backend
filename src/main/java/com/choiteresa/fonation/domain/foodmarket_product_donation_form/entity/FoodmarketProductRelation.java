package com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity;

import com.choiteresa.fonation.domain.product.entity.Product;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "foodmarket_product_relation")
@NoArgsConstructor
@AllArgsConstructor
public class FoodmarketProductRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;


    // FPR many : donation_form one
    @JoinColumn(name = "donation_form_id")
    @ManyToOne
    public ProductDonationForm donationForm;

    @ManyToOne
    @JoinColumn(name = "foodmarket_id")
    public FoodMarket foodMarket;

    @OneToMany(mappedBy = "relation")
    private List<Product> products;
    private Long foodMarketId;  // 이름 변경
    private Date selectedDate;

    public FoodmarketProductRelation() {}

    public FoodmarketProductRelation(ProductDonationForm donationForm, Long foodMarketId) {  // 파라미터 이름 변경
        this.productDonationForm = donationForm;
        this.foodMarketId = foodMarketId;  // 필드 이름 변경
        this.selectedDate = null;
    }
}