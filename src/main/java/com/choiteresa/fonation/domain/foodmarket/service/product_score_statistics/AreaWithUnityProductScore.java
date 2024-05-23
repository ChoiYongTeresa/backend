package com.choiteresa.fonation.domain.foodmarket.service.product_score_statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@ToString
public class AreaWithUnityProductScore {
    private String area;
    private ArrayList<UnityWithProductScore> unityWithProductScoreList;
}
