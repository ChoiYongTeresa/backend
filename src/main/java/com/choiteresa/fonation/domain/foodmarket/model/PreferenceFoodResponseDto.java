package com.choiteresa.fonation.domain.foodmarket.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PreferenceFoodResponseDto {
    String area;
    String code;
    String unitySigngu;
    String foodMarketName;
    String preferFood;
    Integer holdQuantity;
}