package com.choiteresa.fonation.domain.foodmarket_product_donation_form.dto;

import com.choiteresa.fonation.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectedProductDto {
    private Long productId;
    private String productName;
    private int productWeight;
    private int productQuantity;
    private int storeType;
    private Date expireDate;
    private boolean isSelected;


    public static SelectedProductDto fromEntity(Product product){
        return SelectedProductDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productWeight(product.getWeight())
                .productQuantity(product.getQuantity())
                .storeType(product.getStoreType())
                .expireDate(product.getExpireDate())
                .isSelected(product.isSelected())
                .build();
    }
}
