package com.choiteresa.fonation.domain.foodmarket.service.enums;

public enum FoodMarketCodeMapperFilePath {
    AREA_CODE("areaCd_mapper.json"),
    PREFER_FOOD_CODE("preferCnttgClscd_mapper.json"),
    FOOD_MARKET_CODE("spctrCd_mapper.json"),
    FOOD_CENTER_TYPE_CODE("spctrSecd_mapper.json"),
    FOOD_CENTER_TERRITORY("spctrUncd_mapper.json"),
    UNITY_SIGNGU_CODE("unitySignguCd_mapper.json");

    private final String filepath;
    public String getPath(){
        return this.filepath;
    }
    FoodMarketCodeMapperFilePath(String path){
        this.filepath=path;
    }
}
