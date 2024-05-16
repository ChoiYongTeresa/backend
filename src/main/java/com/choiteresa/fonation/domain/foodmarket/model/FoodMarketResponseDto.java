package com.choiteresa.fonation.domain.foodmarket.model;

import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.foodmarket.service.FoodMarketCodeMapper;
import com.choiteresa.fonation.domain.foodmarket.service.enums.FoodMarketCodeMapperFilePath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodMarketResponseDto{
    String name;
    String code;
    String area;
    String unity_signgu;
    String address;
    String detail_address;
    String phone_number;
    String significant;
    String longitude;
    String latitude;

    public FoodMarket toEntity() throws IOException, ParseException {
        FoodMarketCodeMapper codeMapper = new FoodMarketCodeMapper();
        String centerCdoe = codeMapper.convertValueToCode(FoodMarketCodeMapperFilePath.FOOD_MARKET_CODE,name);

        return FoodMarket.builder().
                name(name).
                code(centerCdoe).
                area(area).
                unitySigngu(unity_signgu).
                address(address).
                detailAddress(detail_address).
                phoneNumber(phone_number).
                prohibitedItem(significant).
                longitude(BigDecimal.valueOf(Double.parseDouble(longitude))).
                latitude(BigDecimal.valueOf(Double.parseDouble(latitude))).build();
    }
}