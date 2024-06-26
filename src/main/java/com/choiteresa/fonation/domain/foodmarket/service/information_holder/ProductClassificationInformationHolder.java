package com.choiteresa.fonation.domain.foodmarket.service.information_holder;

import com.choiteresa.fonation.domain.foodmarket.service.enums.FoodMarketCodeMapperFilePath;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

// TODO: Bean 으로 등록하고 싶다..
@Slf4j
public class ProductClassificationInformationHolder extends InformationHolder {
    private ArrayList<String> productLargeClassificationList = new ArrayList<>();
    private ArrayList<String> productMidClassificationList = new ArrayList<>();

    private final String MID_CLASSIFICATION_FILE_NAME;
    private final String LARGE_CLASSIFICATION_FILE_NAME;

    public ProductClassificationInformationHolder() {
        MID_CLASSIFICATION_FILE_NAME = FoodMarketCodeMapperFilePath.PRODUCT_MID_CLASSIFICATION_CODE.getPath();
        LARGE_CLASSIFICATION_FILE_NAME = FoodMarketCodeMapperFilePath.PRODUCT_LARGE_CLASSIFICATION_CODE.getPath();

        init(MID_CLASSIFICATION_FILE_NAME);
        init(LARGE_CLASSIFICATION_FILE_NAME);
    }

    private void init(String filePath){
        cacheUpdate(filePath);
        Reader reader = mapperCache.get(filePath);

        // 분류 물품 불러오기
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        // 파일 경로에 따라 모든 분류물품 저장하기
        if(filePath.equals(MID_CLASSIFICATION_FILE_NAME)){
            log.info("{}", jsonObject.values());
            productMidClassificationList.addAll(jsonObject.values());
        } else{
            log.info("{}",jsonObject.values());
            productLargeClassificationList.addAll(jsonObject.values());
        }
    }

    public ArrayList<String> getProductLargeClassificationList(){
        return this.productLargeClassificationList;
    }

    public ArrayList<String> getProductMidClassificationList(){
        return this.productMidClassificationList;
    }
}
