package com.choiteresa.fonation.domain.product_donation_form.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductRequest {

    private String category;
    private String name;
    private int quantity;
    private int storeType;
    private Date expireDate;
    private int weight;
    private int isSelected;
}
