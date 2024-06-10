package com.choiteresa.fonation.domain.product.Dto;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import com.choiteresa.fonation.domain.product.entity.Product;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationSubmissionDto {
    private ProductDonationForm donationForm;
    private Product product;
    private FoodmarketProductRelation relation;
}
