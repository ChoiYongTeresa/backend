package com.choiteresa.fonation.domain.foodmarket.service.enums;

public enum FoodMarketSortType {
    DEFAULT(0), NEAREST(1), NESSASARY(2);

    private final int value;
    FoodMarketSortType(int v){
        value=v;
    }

    public int value(){
        return this.value;
    }

    public static FoodMarketSortType findByValue(int v){
        return switch (v) {
            case 1 -> NEAREST;
            case 2 -> NESSASARY;
            default -> DEFAULT;
        };
    }
}
