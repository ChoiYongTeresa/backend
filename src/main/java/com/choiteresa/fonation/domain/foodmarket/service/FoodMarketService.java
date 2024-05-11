package com.choiteresa.fonation.domain.foodmarket.service;


import org.springframework.stereotype.Service;

@Service
public class FoodMarketService {
    public void fetchNearbyFoodMarketsSortedByDistance(){
        // TODO: 유저의 지역정보와 기부하려는 물품을 받아, 근처의 푸드마켓 정보를 가져오고, 리스트로 정렬해서 보여주기
    }


    public void sortByDistance(){
        // TODO: 정렬방법 1 -> 현재 위치 정보와 근처의 푸드마켓과의 거리를 계산 후 내림차 순 정렬
    }


    public void sortByPreference(){
        /*
            TODO: 정렬방법 2 -> 유저가 기부하려는 물품과 근처의 푸드마켓 정보를 이용하여,
                선호물품조사API를 사용후 min-max scaling 후에 그 합이 가장 작은 순서대로 내림차 순 정렬
        */
    }

    public void setFoodMarketDataFromAPI(){
        // TODO: 푸드마켓의 정보를 외부 API를 통해 얻어와, 데이터 베이스에 저장
    }
}
