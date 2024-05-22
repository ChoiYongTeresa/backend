package com.choiteresa.fonation.domain.foodmarket.service.information_holder;

import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

public class InformationHolder {
    protected JSONParser jsonParser = new JSONParser();
    protected HashMap<String, Reader> mapperCache = new HashMap<>();

    void cacheUpdate(String filepath) throws IOException {
        // 캐시가 없는 Reader 라면 ClassPathResource를 가져오고 갱신
        FileReader fileReader = new FileReader(new ClassPathResource(filepath).getFile());
        mapperCache.put(filepath, fileReader);
    }
}
