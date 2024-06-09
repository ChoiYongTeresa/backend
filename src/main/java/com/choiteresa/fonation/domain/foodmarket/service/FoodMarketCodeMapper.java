package com.choiteresa.fonation.domain.foodmarket.service;

import com.choiteresa.fonation.domain.foodmarket.service.enums.FoodMarketCodeMapperFilePath;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;

@NoArgsConstructor
public class FoodMarketCodeMapper {
    private JSONParser jsonParser = new JSONParser();
    private HashMap<FoodMarketCodeMapperFilePath, Reader> mapperCache = new HashMap<>();

    public void cacheUpdate(FoodMarketCodeMapperFilePath filepath) {

        // 캐시가 없는 Reader 라면 ClassPathResource를 가져오고 갱신
        try {
            mapperCache.put(filepath, new FileReader(new ClassPathResource(filepath.getPath()).getFile()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String convertCodeToValue(FoodMarketCodeMapperFilePath filepath, String code) throws IOException, ParseException {
        // 코드를 데이터 값으로 매핑하는 함수

        // 캐시가 없는 Reader 라면 ClassPathResource를 가져오고 갱신
        cacheUpdate(filepath);

        Reader reader = mapperCache.get(filepath);
        JSONObject mapper = (JSONObject) jsonParser.parse(reader);
        return (String) mapper.get(code);
    }

    public String convertValueToCode(FoodMarketCodeMapperFilePath filepath, String value) {
        // 코드를 데이터 값으로 매핑하는 함수

        // 캐시가 없는 Reader 라면 ClassPathResource를 가져오고 갱신
        cacheUpdate(filepath);

        Reader reader = mapperCache.get(filepath);
        JSONObject mapper = null;

        try {
            mapper = (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

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
