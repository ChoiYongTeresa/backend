package com.choiteresa.fonation.domain.foodmarket.service;


import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.foodmarket.model.*;
import com.choiteresa.fonation.domain.foodmarket.repository.FoodMarketRepository;
import com.choiteresa.fonation.domain.foodmarket.service.enums.FoodMarketCodeMapperFilePath;
import com.choiteresa.fonation.domain.foodmarket.service.enums.FoodMarketSortType;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodMarketService {

    @Value("${OPEN_API_KEY}")
    private String SERVICE_KEY;
    private FoodMarketCodeMapper codeMapper = new FoodMarketCodeMapper();
    private FoodMarketRepository foodMarketRepository;

    public List<FoodMarket> saveFoodMarketFromRemoteConfig(){
        // 로컬 파이썬 서버 API 요청
        String url = "http://localhost:8000/getFoodmarketInfo";
        FoodMarketApiResponseDto responseDto = new RestTemplate().getForObject(
                url,    // 요청 URL
                FoodMarketApiResponseDto.class
        );

        if(responseDto == null)
            throw new RuntimeException("responseDto not found ");

        // API로 얻어온 푸드마켓 정보를 Entity로 변환
        List<FoodMarket> entityList =
            responseDto.getItems().stream().
                    map(FoodMarketResponseDto::toEntity).toList();

        return foodMarketRepository.saveAll(entityList);
    }
    public void fetchNearbyFoodMarketsSorted(FetchFoodMarketRequestDto dto) throws IOException, ParseException {
        // TODO: 유저의 지역정보와 기부하려는 물품을 받아, 근처의 푸드마켓 정보를 가져오고, 리스트로 정렬해서 보여주기

        PreferFoodModel[] foodList = dto.getFoodList();
        String address = dto.getAddress();

        // 정렬하기
        FoodMarketSortType sortType = FoodMarketSortType.findByValue(dto.getSortType());

        // 최종적으로 지역 내 푸드마켓 정보 리스트 반환
//        switch (sortType){
//            case DEFAULT -> null;
//            case NEAREST -> sortByDistance();
//            case NESSASARY -> sortByPreference();
//        }
    }

    public List<PreferenceFoodDto> fetchFoodMarketPreferenceByOpenApi(String foodName, String address) throws IOException, ParseException {
        // TODO: 선호물풍통계 API 사용해서 현황을 Response DTO로 가져오기
        PreferenceFoodApiRequestDto requestDto =
                PreferenceFoodApiRequestDto.builder().
                        serviceKey(SERVICE_KEY).
                        numOfRows("100").
                        pageNo("1").
                        dataType("json").
                        areaCd(codeMapper.convertValueToCode
                                (FoodMarketCodeMapperFilePath.AREA_CODE,convertAddressToArea(address))).
                        unitySignguCd("json").
                        preferCnttgClscd(codeMapper.convertValueToCode
                                (FoodMarketCodeMapperFilePath.PREFER_FOOD_CODE,foodName))
                        .build(); // 한 페이지 결과 수


        // 외부 선호물품통계 API 호출 후 데이터 받아오기
        String url = "http://apis.data.go.kr/B460014/foodBankInfoService2/getPreferInfo";
        PreferenceFoodApiResponseDto responseBody = new RestTemplate().getForObject(
                url,    // 요청 URL
                PreferenceFoodApiResponseDto.class,
                requestDto
        );

        return responseBody.getPreferenceFoodList();
    }
    public void sortByDistance(String address){
        // TODO: 정렬방법 1 -> 현재 위치 정보와 근처의 푸드마켓과의 거리를 계산 후 내림차 순 정렬

        // 지역 정보 가져오기 (ex. 대전)
        String area = convertAddressToArea(address);


    }

    public void sortByPreference(PreferFoodModel[] givenFoodList, String address) throws IOException, ParseException {
        /*
            TODO: 정렬방법 2 -> 유저가 기부하려는 물품과 근처의 푸드마켓 정보를 이용하여,
                선호물품조사API를 사용 후, min-max scaling 후에, 그 합이 가장 작은 순서대로 내림차 순 정렬
        */

        for (PreferFoodModel food : givenFoodList){
            // addres
            List<PreferenceFoodDto> preferenceFoodList =
                    fetchFoodMarketPreferenceByOpenApi(food.getFoodCategory(), address);

            // TODO: 위의 리스트로 나온 센터당 min-max scaling을 진행한 후, 최종 선호도 합 누적
        }
    }

    public void setFoodMarketDataFromAPI(){
        // TODO: 푸드마켓의 정보를 외부 API를 통해 얻어와, 데이터 베이스에 저장
    }

    public String convertAddressToArea(String address){
        return "대전";

        //        StringTokenizer s=new StringTokenizer(address," ");
        //
        //        String area = s.nextToken();
        //
        //        String[] areas = {"중앙", "서울", "부산", "대구", "인천",
        //                "광주", "대전", "울산", "경기", "강원", "충북", "충남", "전북",
        //                "전남", "경북","경남","제주", "교육", "중앙물류", "세종"};

        // TODO: 일치하는 정도 구하기
        // TODO: 위치 정보를 기반으로 지역 정보 쿼리하기, 지금은 딱히 방법이 안떠오름
    }
}
