package com.choiteresa.fonation.domain.foodmarket.service;


import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.foodmarket.model.*;
import com.choiteresa.fonation.domain.foodmarket.repository.FoodMarketRepository;
import com.choiteresa.fonation.domain.foodmarket.service.enums.FoodMarketCodeMapperFilePath;
import com.choiteresa.fonation.domain.foodmarket.service.enums.FoodMarketSortType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodMarketService {

    @Value("${OPEN_API_KEY}")
    private String SERVICE_KEY;
    private FoodMarketCodeMapper codeMapper = new FoodMarketCodeMapper();
    private final FoodMarketRepository foodMarketRepository;

    public FoodMarket fetchFoodMarketById(int id){
        return foodMarketRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
    }
    public List<FoodMarket> saveFoodMarketFromRemoteConfig() throws IOException, ParseException {
        // Python 서버에서 푸드마켓에 대한 정보를 요청

        // 로컬 파이썬 서버 API 요청
        String url = "http://localhost:8000/getFoodmarketInfo";
        FoodMarketApiResponseDto responseDto = new RestTemplate().getForObject(
                url,
                FoodMarketApiResponseDto.class
        );

        if(responseDto == null)
            throw new RuntimeException("responseDto not found ");

        // API로 얻어온 푸드마켓 정보를 Entity로 변환
        List<FoodMarket> entityList = new ArrayList<>();

        for (FoodMarketResponseDto foodMarketResponseDto : responseDto.getItems()) {
            FoodMarket entity = foodMarketResponseDto.toEntity();
            entityList.add(entity);
        }

        return foodMarketRepository.saveAll(entityList);
    }
    public List<FetchFoodMarketResponseDto> fetchNearbyFoodMarketsSorted(FetchFoodMarketRequestDto dto) throws IOException, ParseException {
        // TODO: 유저의 지역정보와 기부하려는 물품을 받아, 근처의 푸드마켓 정보를 가져오고, 리스트로 정렬해서 보여주기

        PreferFoodModel[] foodList = dto.getFoodList();
        String address = dto.getAddress();

        // 정렬하기
        FoodMarketSortType sortType = FoodMarketSortType.findByValue(dto.getSortType());

        List<FoodMarket> neighborFoodMarketList =
                foodMarketRepository.findFoodMarketsByArea(convertAddressToArea(address));

        List<FetchFoodMarketResponseDto> defaultFoodMarketListDto =
                neighborFoodMarketList.stream().map(FoodMarket::toDto).toList();

        // 최종적으로 지역 내 푸드마켓 정보 리스트 반환
        return switch (sortType){
            case DEFAULT -> defaultFoodMarketListDto;
            case NEAREST -> sortByDistance(address);
            case NESSASARY -> sortByPreference(foodList, address);
        };
    }

    public List<FetchFoodMarketResponseDto> sortByDistance(String address){
        // 정렬방법 1 -> 현재 위치 정보와 근처의 푸드마켓과의 거리를 계산 후 내림차 순 정렬

        // 받아온 주소(유저의 주소)에 대한 위도, 경도를 가져오는 파이썬 API 사용
        String url = "http://localhost:8000/fetchCurrentGeolocation?address="+address;
        GeoLocationApiResponseDto responseDto = new RestTemplate().getForObject(
                url,    // 요청 URL
                GeoLocationApiResponseDto.class
        );

        // 지역 정보 가져오기
        String area = convertAddressToArea(address);
        log.info("target: ({},{})", responseDto.getLongitude(),responseDto.getLatitude());

        // 현재 지역(대전) 내의 푸드마켓 리스트에 대한 거리를 구한 후 FoodMarketWithDistance 리스트로 가져오고, 거리를 내림차 순으로 정렬
        List<FoodMarketWithDistance> queryResult =
            foodMarketRepository.findFoodMarketInArea(responseDto.getLongitude(),responseDto.getLatitude(),area);

        List<FetchFoodMarketResponseDto> sortedFoodMarketListByDistance=
                queryResult.stream().sorted((Comparator.comparing(FoodMarketWithDistance::getDistance)))
                        .map(FoodMarketWithDistance::toDto)
                        .toList();

        // 각 랭크 정보 기입하기
        for(int rank=1;rank<sortedFoodMarketListByDistance.size()+1;rank++){
            sortedFoodMarketListByDistance.get(rank-1).setRank(rank);
        }
        queryResult.forEach(
                item->log.info("{},({},{}) Distance : {}",
                        item.getName(),item.getLongitude(),item.getLatitude(),item.getDistance()));

        return sortedFoodMarketListByDistance;
    }

    public List<FetchFoodMarketResponseDto> sortByPreference(PreferFoodModel[] givenFoodList, String address) throws IOException, ParseException {
        /*
            TODO: 정렬방법 2 -> 유저가 기부하려는 물품과 근처의 푸드마켓 정보를 이용하여,
                선호물품조사API를 사용 후, min-max scaling 후에, 그 합이 가장 작은 순서대로 내림차 순 정렬
        */

        HashMap<String, FoodMarketWithPreferenceScore> foodMarketPreferenceScore = new HashMap<>();

        // 해당 지역 내의 푸드마켓 리스트를 조회 후 코드를 해시 테이블에 저장
        String area = convertAddressToArea(address);
        List<FoodMarket> foodMarketList = foodMarketRepository.findFoodMarketsByArea(area);

        foodMarketList.forEach(foodMarket ->
                foodMarketPreferenceScore.put(foodMarket.getCode(),
                        new FoodMarketWithPreferenceScore(foodMarket,0,0.0)));

        // 유저가 기부하려는 물품들의 리스트를 조회
        for (PreferFoodModel food : givenFoodList){
            // 각 푸드마켓에 대한 기부 물품 보유 수량을 조사, 조회 결과에 푸드뱅크가 포함되어 있을 경우 제외, 오름차순 정렬
            List<PreferenceFoodResponseDto> preferenceFoodList =
                    fetchFoodMarketPreferenceByOpenApi(food.getFoodCategory(), address).stream()
                            .filter(info->isFoodMarket(info.getCode()))
                            .sorted((o1, o2) -> o1.getHoldQuantity() - o2.getHoldQuantity())
                            .toList();

            preferenceFoodList.forEach(item->log.info("content: {}",item));

            // 첫 번째 요소의 값이 가장 작고, 맨 끝의 요소가 가장 값이 크므로 min, max를 지정한다.
            // TODO: 만약 이 물품을 선호하지 않는다면 값으로 어떻게 나타낼건가?
            // TODO: min==max일 경우, 어떻게 처리할 것인가?
            int max = preferenceFoodList.get(preferenceFoodList.size()-1).getHoldQuantity();
            int min = preferenceFoodList.get(0).getHoldQuantity();

            // 위의 리스트로 나온 센터당 min-max scaling을 진행한 후, 최종 선호도 합 누적
            for(PreferenceFoodResponseDto preferenceFood : preferenceFoodList){
                String code = preferenceFood.getCode();

                // 해시 테이블에 있는 푸드마켓 조회, 만에하나 없으면 새로 생성하여 저장
                FoodMarketWithPreferenceScore currentMarketScore =
                        foodMarketPreferenceScore.getOrDefault(code,
                                new FoodMarketWithPreferenceScore(foodMarketRepository.findByCode(code).
                                        orElseThrow(()->new RuntimeException("foodmarket not found")),0,0.0));

                double scaledValue = (preferenceFood.getHoldQuantity() - min) / Math.max((double)1,(max - min));
                // 해당 푸드마켓 code에 대한 스코어 갱신하기
                currentMarketScore.setScore(currentMarketScore.getScore()+scaledValue);
                foodMarketPreferenceScore.put(code, currentMarketScore);
            }
        }

        // foodMarketList를 Score 순서대로 갱신
        foodMarketList = foodMarketPreferenceScore.values().stream().
                sorted((o1, o2) -> (int) (o1.getScore()-o2.getScore())).
                map(FoodMarketWithPreferenceScore::getFoodMarket).toList();

        // Debug Message
        foodMarketPreferenceScore.values().
                forEach(item->log.info("{} score: {}",item.getFoodMarket().getName(),item.getScore()));

        // Entity를 DTO로 변환
        List<FetchFoodMarketResponseDto> dtoList =
                foodMarketList.stream().map(FoodMarket::toDto).toList();

        // 랭크 조정
        dtoList.forEach(dto->dto.setRank(dtoList.indexOf(dto)+1));

        return dtoList;
    }


    public List<PreferenceFoodResponseDto> fetchFoodMarketPreferenceByOpenApi(String foodName, String address) throws IOException, ParseException {
        // TODO: 선호물풍통계 API 사용해서 현황을 Response DTO로 가져오기
        PreferenceFoodApiRequestDto requestDto =
                PreferenceFoodApiRequestDto.builder().
                        serviceKey(SERVICE_KEY).
                        numOfRows("100").
                        pageNo("1").
                        dataType("json").
                        areaCd(codeMapper.convertValueToCode
                                (FoodMarketCodeMapperFilePath.AREA_CODE,convertAddressToArea(address))).
                        preferCnttgClscd(codeMapper.convertValueToCode
                                (FoodMarketCodeMapperFilePath.PREFER_FOOD_CODE,foodName))
                        .build(); // 한 페이지 결과 수

        // 외부 선호물품통계 API 호출 후 데이터 받아오기
        // TODO: 응답을 받는 것에 대해 오류가 있다면 예외처리 해주기
        String params = requestDto.getParamString()+"&SG_APIM=2ug8Dm9qNBfD32JLZGPN64f3EoTlkpD8kSOHWfXpyrY";
        String url = "https://openapi.foodbank1377.org/foodBankInfoService/getPreferInfo"+params;

        PreferenceFoodApiResponseDto apiResponseDto = new RestTemplate().getForObject(
                        url,
                        PreferenceFoodApiResponseDto.class);


        return apiResponseDto.getPreferenceFoodList();
    }

    public boolean isFoodMarket(String centerCode){
        // 해당이름을 가진 센터 이름이 푸드마켓인지 아닌지 확인, 현재는 데이터베이스 조회로 구현
        return foodMarketRepository.findByCode(centerCode).isPresent();
    }
    public String convertAddressToArea(String address){
        // TODO: 일치하는 정도 구하기
        // TODO: 위치 정보를 기반으로 지역 정보 쿼리하기, 지금은 딱히 방법이 안떠오름

        return "대전";
    }
}
