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

        // ������ ���� �ִ� �迭
        JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

        // ���� ���� ��������
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);

        // ���� ���� �� �ñ��� Array
        String area = (String)jsonObject.get("area");
        JSONArray unitysignguArray = (JSONArray)jsonObject.get("unitysigngu");

        // �������� ����
        areaTreeStructure.put(area, new ArrayList<>());

        // �ñ��� ���� ��ȸ �� �ؽøʿ� ����
        unitysignguArray.forEach(object -> areaTreeStructure.get(area).add((String)object));
    }

    public ArrayList<String> getAreaList(){
        return new ArrayList<>(areaTreeStructure.keySet());
    }

    public ArrayList<String> getUnitySignguListByArea(String area){
        return areaTreeStructure.get(area);
    }
}
