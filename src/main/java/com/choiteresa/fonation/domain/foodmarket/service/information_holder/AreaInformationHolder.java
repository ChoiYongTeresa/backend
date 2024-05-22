package com.choiteresa.fonation.domain.foodmarket.service.information_holder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AreaInformationHolder extends InformationHolder{

    private HashMap<String, ArrayList<String>> areaTreeStructure = new HashMap<>();
    private final String AREA_FILE_NAME = "area.json";


    public AreaInformationHolder() throws IOException, ParseException {
        init();
    }

    private void init() throws IOException, ParseException {
        cacheUpdate(AREA_FILE_NAME);
        Reader reader = mapperCache.get(AREA_FILE_NAME);

        // 지역을 갖고 있는 배열
        JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

        // 지역 정보 가져오기
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);

        // 지역 정보 및 시군구 Array
        String area = (String)jsonObject.get("area");
        JSONArray unitysignguArray = (JSONArray)jsonObject.get("unitysigngu");

        // 지역정보 저장
        areaTreeStructure.put(area, new ArrayList<>());

        // 시군구 정보 순회 후 해시맵에 저장
        unitysignguArray.forEach(object -> areaTreeStructure.get(area).add((String)object));
    }

    public ArrayList<String> getAreaList(){
        return new ArrayList<>(areaTreeStructure.keySet());
    }

    public ArrayList<String> getUnitySignguListByArea(String area){
        return areaTreeStructure.get(area);
    }
}
