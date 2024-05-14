package com.choiteresa.fonation.domain.foodmarket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodMarketApiResponseDto {
    List<FoodMarketResponseDto> items;
    int count;
}


