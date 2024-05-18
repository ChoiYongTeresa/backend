package com.choiteresa.fonation.domain.foodmarket.service;

import com.choiteresa.fonation.domain.foodmarket.service.enums.FoodMarketCodeMapperFilePath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;

public class FoodMarketCodeMapper {
    private JSONParser jsonParser = new JSONParser();
    private HashMap<FoodMarketCodeMapperFilePath, Reader> mapperCache = new HashMap<>();

    public void cacheUpdate(FoodMarketCodeMapperFilePath filepath) throws IOException {

        // 캐시가 없는 Reader 라면 ClassPathResource를 가져오고 갱신
        mapperCache.put(filepath, new FileReader(new ClassPathResource(filepath.getPath()).getFile()));
    }
    public String convertCodeToValue(FoodMarketCodeMapperFilePath filepath, String code) throws IOException, ParseException {
        // 코드를 데이터 값으로 매핑하는 함수

        // 캐시가 없는 Reader 라면 ClassPathResource를 가져오고 갱신
        cacheUpdate(filepath);

        Reader reader = mapperCache.get(filepath);
        JSONObject mapper = (JSONObject) jsonParser.parse(reader);
        return (String) mapper.get(code);
    }

    public String convertValueToCode(FoodMarketCodeMapperFilePath filepath, String value) throws IOException, ParseException {
        // 코드를 데이터 값으로 매핑하는 함수

        // 캐시가 없는 Reader 라면 ClassPathResource를 가져오고 갱신
        cacheUpdate(filepath);

        Reader reader = mapperCache.get(filepath);
        JSONObject mapper = (JSONObject) jsonParser.parse(reader);

        if (mapper.containsValue(value)) {
            // 찾아온 JSON 파일에 대한 key 집합을 가져오기
            Iterator iterator = mapper.keySet().stream().iterator();

            // key에 대해 value가 나오는 구간을 찾아 해당 key(code)를 반환하기
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String found = (String) mapper.get(key);
                if (found.equals(value)) {
                    return key;
                }
            }
        }

        return null;
    }
}
