package com.choiteresa.fonation.domain.product_donation_form.Dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class ProductRequest {

    private String category;
    private String name;
    private int quantity;
    private int storeType;
    private Date expireDate;
    private int weight;
    private boolean isSelected;

    public boolean getIsSelected() {
        return isSelected;
    }
}
