package com.choiteresa.fonation.domain.foodmarket.model;

import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodMarketResponseDto{
    String name;
    String area;
    String unity_signgu;
    String address;
    String detail_address;
    String phone_number;
    String significant;
    String longitude;
    String latitude;

    public FoodMarket toEntity(){
        return FoodMarket.builder().
                name(name).
                area(area).
                unitySigngu(unity_signgu).
                address(address).
                detailAddress(detail_address).
                phoneNumber(phone_number).
                prohibitedItem(significant).
                longitude(Double.parseDouble(longitude)).
                latitude(Double.parseDouble(latitude)).build();
    }
}