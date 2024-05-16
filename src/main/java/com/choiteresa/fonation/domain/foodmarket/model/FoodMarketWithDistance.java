package com.choiteresa.fonation.domain.foodmarket.model;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public interface FoodMarketWithDistance {
    Integer getId();
    BigDecimal getLatitude();
    BigDecimal getLongitude();
    BigDecimal getDistance();
    String getName();
    String getArea();
    String getUnity_signgu();
    String getAddress();
    String getDetail_address();
    String getPhone_number();
    String getProhibited_item();

    default FetchFoodMarketResponseDto toDto(){
            return FetchFoodMarketResponseDto.builder().
                    id(getId()).
                    foodMarketName(getName()).
                    phoneNumber(getPhone_number()).
                    address(getAddress()).
                    detailAddress(getDetail_address()).build();
    }
}
