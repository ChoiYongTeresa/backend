package com.choiteresa.fonation.domain.foodmarket.service.enums;

public enum FoodMarketCodeMapperFilePath {
    AREA_CODE("classpath:areaCd_mapper.json"),
    PREFER_FOOD_CODE("classpath:preferCnttgClscd_mapper.json"),
    FOOD_MARKET_CODE("classpath:spctrCd_mapper.json"),
    FOOD_CENTER_TYPE_CODE("classpath:spctrSecd_mapper.json"),
    FOOD_CENTER_TERRITORY("classpath:spctrUncd_mapper.json"),
    UNITY_SIGNGU_CODE("classpath:unitySignguCd_mapper.json");

    private final String filepath;
    public String getPath(){
        return this.filepath;
    }
    FoodMarketCodeMapperFilePath(String path){
        this.filepath=path;
    }
}
