package com.choiteresa.fonation.domain.foodmarket.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FetchFoodMarketResponseDto {
    Long id;
    Integer rank;
    String foodMarketName;
    String phoneNumber;
    String address;
    String detailAddress;

    public void setRank(int r){
        this.rank=r;
    }
}
