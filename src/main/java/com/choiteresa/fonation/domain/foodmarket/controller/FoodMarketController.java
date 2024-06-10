package com.choiteresa.fonation.domain.foodmarket.controller;

import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.foodmarket.model.FetchFoodMarketRequestDto;
import com.choiteresa.fonation.domain.foodmarket.model.FetchFoodMarketResponseDto;
import com.choiteresa.fonation.domain.foodmarket.model.ProductStatusForGraphResponseDto;
import com.choiteresa.fonation.domain.foodmarket.model.UpdateProhibitedItemRequest;
import com.choiteresa.fonation.domain.foodmarket.service.FoodMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donations/center")
@Slf4j
public class FoodMarketController {

    private final FoodMarketService foodMarketService;
  
//    @Autowired
//    public FoodMarketController(FoodMarketService foodMarketService) {
//        this.foodMarketService = foodMarketService;
//    }

    @PostMapping("admin/donations/{id}/bannedProduct")
    public ResponseEntity<FetchFoodMarketResponseDto> updateProhibitedItem(@PathVariable Integer id, @RequestBody UpdateProhibitedItemRequest request) {
        FetchFoodMarketResponseDto updatedFoodMarket = foodMarketService.updateProhibitedItem(id, request.getProhibitedItem());
        return ResponseEntity.ok(updatedFoodMarket);
    }
  
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

    @PostMapping("/readAll")
    public ResponseEntity fetchFoodMarketList(@RequestBody FetchFoodMarketRequestDto dto) throws IOException, ParseException, InterruptedException {
        log.info("{}",dto);

        List<FetchFoodMarketResponseDto> fetchFoodMarketResponseDtoList =
                foodMarketService.fetchNearbyFoodMarketsSorted(dto);

        log.info("{}",fetchFoodMarketResponseDtoList);

        return  ResponseEntity.ok().body(fetchFoodMarketResponseDtoList);
    }

    @GetMapping("/statistics")
    public ResponseEntity fetchAreaProductStatusPerUnity(@RequestParam String area) throws IOException, ParseException {
        if(!area.equals("대전")){
            return ResponseEntity.notFound().build();
        }

        List<ProductStatusForGraphResponseDto> responseDto =
                foodMarketService.fetchProductStatusForGraph(area);

        return ResponseEntity.ok().body(responseDto);
    }
}

