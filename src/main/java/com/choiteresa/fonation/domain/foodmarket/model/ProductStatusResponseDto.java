package com.choiteresa.fonation.domain.foodmarket.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ProductStatusResponseDto {
    String area; // 지역
    String code; // 푸드마켓코드
    String unitySigngu; // 시군구
    String foodMarketName; // 푸드마켓 이름
    String itemClassificationLarge; // 물품종대분류
    String itemClassificationMid; // 물품종중분류
    Integer amountOfProduct; // 물품 갯수
    String amountOfMoney; // 기부 받은 금액
}