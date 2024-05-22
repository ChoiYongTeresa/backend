package com.choiteresa.fonation.domain.foodmarket.model;


import com.choiteresa.fonation.domain.foodmarket.service.FoodMarketCodeMapper;
import com.choiteresa.fonation.domain.foodmarket.service.enums.FoodMarketCodeMapperFilePath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PreferenceFoodApiResponseDto {
    PreferenceFoodResponseFrame response;

    public List<PreferenceFoodResponseDto> getPreferenceFoodList(){
        FoodMarketCodeMapper foodMarketCodeMapper = new FoodMarketCodeMapper();

        return this.getResponse().getBody().getItems().stream()
                .map(itemFrame-> {
                    try {
                        return PreferenceFoodResponseDto.builder().
                                area(foodMarketCodeMapper.
                                        convertCodeToValue(FoodMarketCodeMapperFilePath.AREA_CODE,itemFrame.areaCd)).
                                code(itemFrame.spctrCd).
                                unitySigngu(foodMarketCodeMapper.
                                        convertCodeToValue(FoodMarketCodeMapperFilePath.UNITY_SIGNGU_CODE,itemFrame.unitySignguCd)).
                                foodMarketName(foodMarketCodeMapper.
                                        convertCodeToValue(FoodMarketCodeMapperFilePath.FOOD_MARKET_CODE,itemFrame.spctrCd)).
                                preferFood(foodMarketCodeMapper.
                                        convertCodeToValue(FoodMarketCodeMapperFilePath.PREFER_FOOD_CODE,itemFrame.preferCnttgClscd)).
                                holdQuantity(Integer.parseInt(itemFrame.holdQy)).build();
                    } catch (IOException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class PreferenceFoodResponseFrame{
    PreferenceFoodHeaderFrame header;
    PreferenceFoodBodyFrame body;
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class PreferenceFoodHeaderFrame {
    String resultCode;
    String resultMsg;
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class PreferenceFoodBodyFrame {
    String dataType;
    List<PreferenceFoodItemFrame> items;
    Integer pageNo;
    Integer numOfRows;
    Integer totalCount;
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class PreferenceFoodItemFrame {
    String areaCd;
    String unitySignguCd;
    String spctrCd;
    String preferCnttgClscd;
    String holdQy;
}

