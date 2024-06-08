package com.choiteresa.fonation.domain.foodmarket_product_donation_form.dto;

import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectedProductResponseDto {
    private Long foodMarketID;
    private String foodMarketName;
    private List<SelectedProductDto> SelectedProductList;

    public static SelectedProductResponseDto fromEntity(FoodMarket market, List<SelectedProductDto> products){
        return SelectedProductResponseDto.builder()
                .foodMarketID(market.getId())
                .foodMarketName(market.getName())
                .SelectedProductList(products)
                .build();
    }
}
