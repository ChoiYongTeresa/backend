package com.choiteresa.fonation.domain.foodmarket_product_donation_form.controller;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.dto.MarketSelectionRequestDto;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.dto.MarketSelectionResponseDto;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.dto.SelectedProductResponseDto;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.dto.SelectedProductRequestDto;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.service.FPService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/donation")
@Controller
@AllArgsConstructor
public class FPController {

    private final FPService FPService;

    @GetMapping("/selected_info")
    @ResponseBody
    public ResponseEntity<List<SelectedProductResponseDto>> getSelectedProducts (@RequestParam(value = "donationFormId") Long listRequest){
        List<FoodmarketProductRelation> relations = FPService.findFPRelation(listRequest);
        List<SelectedProductResponseDto> listResponse = FPService.findSelectedProductList(relations);
        return ResponseEntity.ok(listResponse);
    }

    @PostMapping("selection")
    @ResponseBody
    public ResponseEntity<MarketSelectionResponseDto> selectMarket (@RequestBody MarketSelectionRequestDto selectRequest){
        return ResponseEntity.ok(FPService.selectMarket(selectRequest));
    }
}
