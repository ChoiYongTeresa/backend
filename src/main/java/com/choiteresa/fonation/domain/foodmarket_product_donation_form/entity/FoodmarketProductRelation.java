package com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity;

import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.product.entity.Product;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "food_market_product_relation")
@NoArgsConstructor
@AllArgsConstructor
public class FoodmarketProductRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;


    // FPR many : donation_form one
    @JoinColumn(name = "product_donation_form_id")
    @ManyToOne
    public ProductDonationForm donationForm;

    @ManyToOne
    @JoinColumn(name = "food_market_id")
    public FoodMarket foodMarket;

    @OneToMany(mappedBy = "relation")
    private List<Product> products;

    @Column
    private Date selectedDate;  // 기부자가 해당 푸드 마켓을 선정한 날짜

    @Column(nullable = true)
    private Date approvedDate; // 관리자가 해당 푸드 마켓을 승인/거부한 날짜 (null이면 아직 승인/거부되지 않은 상태)

    public FoodmarketProductRelation(ProductDonationForm donationForm, FoodMarket foodMarketId) {  // 파라미터 이름 변경
        this.donationForm = donationForm;
        this.foodMarket = foodMarket;  // 필드 이름 변경
        this.selectedDate = null;
        this.approvedDate = null;
    }
}