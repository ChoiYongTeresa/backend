package com.choiteresa.fonation.domain.foodmarket.service.product_score_statistics;

import com.choiteresa.fonation.domain.foodmarket.service.information_holder.ProductClassificationInformationHolder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Setter
@Getter
@ToString
public class ProductScore {
    private ArrayList<ClassificationWithCount> productLargeClassificationScoreList;
    private ArrayList<ClassificationWithCount> productMidClassificationScoreList;
    private ProductClassificationInformationHolder productClassificationInformationHolder;

    ProductScore(ProductClassificationInformationHolder holder) {
        this.productClassificationInformationHolder = holder;
        this.productLargeClassificationScoreList = new ArrayList<>();
        this.productMidClassificationScoreList = new ArrayList<>();
        init();
    }

    private void init() {
        ArrayList<String> prMidList =
                productClassificationInformationHolder.getProductMidClassificationList();
        ArrayList<String> prLargeList =
                productClassificationInformationHolder.getProductLargeClassificationList();


        // 각 물품분류에 대한 갯수 초기화
        prLargeList.forEach(product ->
                productLargeClassificationScoreList.add(new ClassificationWithCount(product, 0)));
        prMidList.forEach(product ->
                productMidClassificationScoreList.add(new ClassificationWithCount(product, 0)));
    }

    private ClassificationWithCount findLargeClassificationWithCount(String classificationName) {
        return productLargeClassificationScoreList.stream()
                .filter(object -> object.getProductClassification().equals(classificationName))
                .findFirst().orElse(null);
    }

    private ClassificationWithCount findMidClassificationWithCount(String classificationName) {
        // null 일 수도 있음

        return productMidClassificationScoreList.stream()
                .filter(object -> object.getProductClassification().equals(classificationName))
                .findFirst().orElse(null);
    }

    public void updateLarge(String productClassification, int score) {
        // 물품 분류에 대한 스코어(카운트) 업데이트
        ClassificationWithCount classificationWithCount =
                findLargeClassificationWithCount(productClassification);

        if (classificationWithCount == null){
            return;
        }
        classificationWithCount.sum(score);
    }

    public void updateMid(String productClassification, int score) {
        // 물품 분류에 대한 스코어(카운트) 업데이트
        ClassificationWithCount classificationWithCount =
                findMidClassificationWithCount(productClassification);

        if (classificationWithCount == null){
            return;
        }

        classificationWithCount.sum(score);
    }
}
