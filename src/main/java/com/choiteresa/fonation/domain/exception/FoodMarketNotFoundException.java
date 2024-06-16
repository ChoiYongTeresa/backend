package com.choiteresa.fonation.domain.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FoodMarketNotFoundException extends RuntimeException{
    private final String message = "FoodMarket not found";
}
