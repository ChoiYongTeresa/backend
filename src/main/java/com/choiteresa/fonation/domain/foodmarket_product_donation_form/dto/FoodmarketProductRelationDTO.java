package com.choiteresa.fonation.domain.foodmarket_product_donation_form.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FoodmarketProductRelationDTO {
    private Long id;
    private Date approvedDate;
    private String foodMarketName;
    public FoodmarketProductRelationDTO(Long id, Date approvedDate, String foodMarketName) {
        this.id = id;
        this.approvedDate = approvedDate;
        this.foodMarketName = foodMarketName;
    }
}
