package com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity;

import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class FoodmarketProductRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "product_donation_form_id", nullable = false)
    public ProductDonationForm productDonationForm;

    private Long foodMarketId;  // �̸� ����
    private Date selectedDate;

    public FoodmarketProductRelation() {}

    public FoodmarketProductRelation(ProductDonationForm donationForm, Long foodMarketId) {  // �Ķ���� �̸� ����
        this.productDonationForm = donationForm;
        this.foodMarketId = foodMarketId;  // �ʵ� �̸� ����
        this.selectedDate = null;
    }
}