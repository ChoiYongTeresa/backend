package com.choiteresa.fonation.domain.foodmarket.entity;


import com.choiteresa.fonation.domain.foodmarket.model.FetchFoodMarketResponseDto;
import com.choiteresa.fonation.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

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
    public Long id;

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

    @Setter
    @Column
    public String prohibitedItem;

    @Column(precision =16, scale = 13)
    public BigDecimal longitude;

    @Column(precision =16, scale = 13)
    public BigDecimal latitude;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    public Member admin;

    public FetchFoodMarketResponseDto toDto(){
        return FetchFoodMarketResponseDto.builder().
                id(id).
                foodMarketName(name).
                phoneNumber(phoneNumber).
                address(address).
                detailAddress(detailAddress).build();
    }
}
