package com.choiteresa.fonation.domain.foodmarket.controller;


import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.foodmarket.model.FetchFoodMarketRequestDto;
import com.choiteresa.fonation.domain.foodmarket.model.FetchFoodMarketResponseDto;
import com.choiteresa.fonation.domain.foodmarket.service.FoodMarketService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donations/center")
public class FoodMarketController {

    private final FoodMarketService foodMarketService;


    @GetMapping("/{marketId}")
    public ResponseEntity fetchFoodMarket(@PathVariable Integer marketId){
        return ResponseEntity.ok().body(foodMarketService.fetchFoodMarketById(marketId));
    }
    @GetMapping("/dataInit")
    public ResponseEntity createFoodMarket() throws IOException, ParseException {
        List<FoodMarket> foodMarkets =
                foodMarketService.saveFoodMarketFromRemoteConfig();

        return ResponseEntity.ok().body(foodMarkets);
    }

    @GetMapping("/readAll")
    public ResponseEntity fetchFoodMarketList(@RequestBody FetchFoodMarketRequestDto dto) throws IOException, ParseException, InterruptedException {
        List<FetchFoodMarketResponseDto> fetchFoodMarketResponseDtoList =
                foodMarketService.fetchNearbyFoodMarketsSorted(dto);

        return  ResponseEntity.ok().body(fetchFoodMarketResponseDtoList);
    }
}


