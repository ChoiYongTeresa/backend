package com.choiteresa.fonation.domain.foodmarket.model;


import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FoodMarketWithPreferenceScore {
    FoodMarket foodMarket;
    int rank;
    Double score;
}
