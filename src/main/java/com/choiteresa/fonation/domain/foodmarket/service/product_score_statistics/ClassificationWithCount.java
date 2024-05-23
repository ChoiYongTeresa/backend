package com.choiteresa.fonation.domain.foodmarket.service.product_score_statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ClassificationWithCount {
    private String productClassification;
    private Integer count;

    public void sum(int value) {
        this.count += value;
    }
}
