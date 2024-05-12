package com.choiteresa.fonation.domain.foodmarket.service;

import com.choiteresa.fonation.domain.foodmarket.service.enums.FoodMarketCodeMapperFilePath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;

public class FoodMarketCodeMapper {
    private JSONParser jsonParser = new JSONParser();
    private HashMap<FoodMarketCodeMapperFilePath,Reader> mapperCache = new HashMap<>();
    public String convertCodeToValue(FoodMarketCodeMapperFilePath filepath,String code) throws IOException, ParseException {
        // �ڵ带 ������ ������ �����ϴ� �Լ�

        // ĳ�ð� ���� Reader ��� ����
        if(!mapperCache.containsKey(filepath)){
            mapperCache.put(filepath,new FileReader(filepath.getPath()));
        }

        Reader reader = mapperCache.get(filepath);
        JSONObject mapper = (JSONObject) jsonParser.parse(reader);
        return (String) mapper.get(code);
    }

    public String convertValueToCode(FoodMarketCodeMapperFilePath filepath, String value) throws IOException, ParseException {
        // �ڵ带 ������ ������ �����ϴ� �Լ�
        // ĳ�ð� ���� Reader ��� ����
        if(!mapperCache.containsKey(filepath)){
            mapperCache.put(filepath,new FileReader(filepath.getPath()));
        }

        Reader reader = mapperCache.get(filepath);
        JSONObject mapper = (JSONObject) jsonParser.parse(reader);

        if(mapper.containsValue(value)){
            // ã�ƿ� JSON ���Ͽ� ���� key ������ ��������
            Iterator iterator= mapper.keySet().stream().iterator();

            // key�� ���� value�� ������ ������ ã�� �ش� key(code)�� ��ȯ�ϱ�
            while(iterator.hasNext()){
                String key = (String)iterator.next();
                String found = (String)mapper.get(key);
                if(found.equals(value)){
                    return key;
                }
            }
        }else{
            throw new RuntimeException("not found in "+filepath.getPath()+"_mapper.json: "+value);
        }

        return null;
    }
}
