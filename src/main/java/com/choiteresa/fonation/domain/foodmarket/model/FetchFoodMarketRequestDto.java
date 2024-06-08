package com.choiteresa.fonation.domain.foodmarket.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@AllArgsConstructor
@ToString
public class FetchFoodMarketRequestDto {
    int sortType; // 정렬 정보
    String address; // 주소
    BigDecimal latitude;
    BigDecimal longitude;
    PreferFoodModel[] foodList; // 음식 리스트(음식 카테고리, 갯수)
}
