package com.choiteresa.fonation.domain.foodmarket.model;


import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
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
public class ProductStatusApiResponseDto {
    ProductStatusApiResponse response;

    public int getTotalResultNum(){
        return response.getBody().getTotalCount();
    }
    public List<ProductStatusResponseDto> getProduectStatusList(FoodMarket foodMarket){
        FoodMarketCodeMapper mapper = new FoodMarketCodeMapper();

        return this.getResponse().getBody().getItems().stream()
                .map(itemFrame-> {
                    try {
                        return ProductStatusResponseDto.builder()
                                .area(foodMarket.getArea())
                                .code(foodMarket.getCode())
                                .unitySigngu(foodMarket.getUnitySigngu())
                                .foodMarketName(foodMarket.getName())
                                .itemClassificationLarge(mapper.convertCodeToValue(
                                                FoodMarketCodeMapperFilePath.PRODUCT_LARGE_CLASSIFICATION_CODE,
                                                itemFrame.getCnttgLclasCd()))
                                .itemClassificationMid(mapper.convertCodeToValue(
                                        FoodMarketCodeMapperFilePath.PRODUCT_MID_CLASSIFICATION_CODE,
                                                itemFrame.getCnttgMlsfcCd()))
                                .amountOfProduct(Integer.parseInt(itemFrame.getCnttgQy()))
                                .amountOfMoney(itemFrame.getAcntbkAmt())
                                .build();
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
class ProductStatusApiResponse {
    ProductStatusHeaderFrame header;
    ProductStatusBodyFrame body;
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class ProductStatusHeaderFrame {
    String resultCode;
    String resultMsg;
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class ProductStatusBodyFrame {
    String dataType;
    List<ProductStatusItemFrame> items;
    Integer pageNo;
    Integer numOfRows;
    Integer totalCount;
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class ProductStatusItemFrame {
    String stdrYm;
    String distbTmlmtUseYn;
    String injryGoodsIntrcpYn;
    String foodYn;
    String cnttgLclasCd;
    String cnttgMlsfcCd;
    String cnttgQy;
    String acntbkAmt;
}

