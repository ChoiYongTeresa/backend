package com.choiteresa.fonation.domain.foodmarket.entity;


import com.choiteresa.fonation.domain.foodmarket.model.FetchFoodMarketResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FoodMarket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer id;

    @Column
    public String code;

    @Column
    public String name;

    @Column
    public String area;

    @Column
    public String unitySigngu;

    @Column
    public String address;

    @Column
    public String detailAddress;

    @Column
    public String phoneNumber;

    @Column
    public String prohibitedItem;

    @Column(precision =16, scale = 13)
    public BigDecimal longitude;

    @Column(precision =16, scale = 13)
    public BigDecimal latitude;

    public FetchFoodMarketResponseDto toDto(){
        return FetchFoodMarketResponseDto.builder().
                id(id).
                foodMarketName(name).
                phoneNumber(phoneNumber).
                address(address).
                detailAddress(detailAddress).build();
    }

}
