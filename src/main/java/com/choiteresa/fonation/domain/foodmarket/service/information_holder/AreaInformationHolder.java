package com.choiteresa.fonation.domain.foodmarket.service.information_holder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class AreaInformationHolder extends InformationHolder{

    private ArrayList<AreaWithUnity> areaWithUnityList = new ArrayList<>();
    private final String AREA_FILE_NAME = "area.json";


    public AreaInformationHolder(){
        init();
    }

    private void init() {
        cacheUpdate(AREA_FILE_NAME);
        Reader reader = mapperCache.get(AREA_FILE_NAME);

        // 지역을 갖고 있는 배열
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        for (Object object : jsonArray){
            // 지역 정보 가져오기
            JSONObject jsonObject = (JSONObject) object;

            // 지역 정보 및 시군구 Array
            String area = (String)jsonObject.get("area");
            JSONArray unitysignguArray = (JSONArray)jsonObject.get("unitySigngu");

            log.info("area: {}",area);
            // 지역정보 저장
            areaWithUnityList.add(new AreaWithUnity(area,new ArrayList<>(unitysignguArray)));
        }
    }

    public List<String> getAreaList(){
        return areaWithUnityList.stream().map(object->object.getArea()).toList();
    }

    public ArrayList<String> getUnityByArea(String area){
        return areaWithUnityList.stream().filter(object->object.getArea().equals(area))
                .findFirst().orElseThrow(()->new RuntimeException("not found: "+area)).getUnityList();
    }
}

@Getter
@AllArgsConstructor
class AreaWithUnity{
    String area;
    ArrayList<String> unityList;
}
