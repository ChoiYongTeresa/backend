package com.choiteresa.fonation.domain.foodmarket.service.information_holder;

import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.HashMap;

public class InformationHolder {
    protected JSONParser jsonParser = new JSONParser();
    protected HashMap<String, Reader> mapperCache = new HashMap<>();

    void cacheUpdate(String filepath) throws IOException {
        // 캐시가 없는 Reader 라면 ClassPathResource를 가져오고 갱신
        FileInputStream fileReader = new FileInputStream(new ClassPathResource(filepath).getFile());
        InputStreamReader inputStreamReader =new InputStreamReader(fileReader,"UTF-8");
        BufferedReader reader = new BufferedReader(inputStreamReader);

        mapperCache.put(filepath, reader);
    }
}
