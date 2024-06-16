package com.choiteresa.fonation.domain.foodmarket_product_donation_form.service;

import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.foodmarket.repository.FoodMarketRepository;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.dto.*;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.repository.FPRepository;
import com.choiteresa.fonation.domain.product.entity.Product;
import com.choiteresa.fonation.domain.product.repository.ProductRepository;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import com.choiteresa.fonation.domain.product_donation_form.repository.ProductDonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class FPService {
    private final FPRepository FPRepository;
    private final FoodMarketRepository foodmarketRepository;
    private final ProductRepository productRepostiroy;
    private final ProductDonationRepository productDonationRepository;

    public List<FoodmarketProductRelation> findFPRelation(SelectedProductRequestDto productsRequest){
        Optional<ProductDonationForm> form = productDonationRepository.findById(productsRequest.getDonationFormId());
        Optional<List<FoodmarketProductRelation>> relations = FPRepository.findByDonationForm(form.get());

        if(relations.isPresent()) {
            return relations.get();
        } else {
            return null;
        }
    }

    public List<SelectedProductResponseDto> findSelectedProductList(List<FoodmarketProductRelation> relations){

        List<SelectedProductDto> productList = new ArrayList<>();
        List<SelectedProductResponseDto> result = new ArrayList<>();

        for(FoodmarketProductRelation relation : relations){
            Optional<FoodMarket> market = foodmarketRepository.findById(relation.getFoodMarket().getId());
            Optional<List<Product>> products = productRepostiroy.findByRelation(relation);

            if(products.isPresent() &&market.isPresent()){
                for(Product product : products.get()){
                    productList.add(SelectedProductDto.fromEntity(product));
                }
                result.add(SelectedProductResponseDto.fromEntity(market.get(),productList));
            }

        }

        return result;

    }
    public MarketSelectionResponseDto selectMarket(MarketSelectionRequestDto selectRequest){
        Optional<FoodmarketProductRelation> relationOptional = FPRepository.findByFoodMarketIdAndDonationFormId(
                selectRequest.getFoodMarketId(), selectRequest.getDonationFormId());
        if (relationOptional.isPresent()) {
            FoodmarketProductRelation relation = relationOptional.get();
            relation.setSelectedDate(new Date());
            FPRepository.save(relation);
            return MarketSelectionResponseDto.fromEntity(1);
        } else {
            return MarketSelectionResponseDto.fromEntity(-1);
        }

    }



}
