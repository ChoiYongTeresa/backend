package com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity;

import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodmarketProductRelation {
// id, donation_form_id, foodmarket_id, selected_date

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer id;

    // FPR many : donation_form one
    @JoinColumn(name = "donation_form_id")
    @ManyToOne
    public ProductDonationForm donationFormId;

    @ManyToOne
    @JoinColumn(name = "foodmarket_id")
    public FoodMarket foodMarketId;

    private Date selectedDate;
}
