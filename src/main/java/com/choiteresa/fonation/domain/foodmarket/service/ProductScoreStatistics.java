package com.choiteresa.fonation.domain.foodmarket.service;

import com.choiteresa.fonation.domain.foodmarket.service.information_holder.AreaInformationHolder;
import com.choiteresa.fonation.domain.foodmarket.service.information_holder.ProductClassificationInformationHolder;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class ProductScoreStatistics {

    // 물품 대분류 스코어
    private final HashMap<String,HashMap<String,ProductScore>> areaProductLargeScore = new HashMap<>();

    // 물품 중분류 스코어
    private final HashMap<String,HashMap<String,ProductScore>> areaProductMidScore = new HashMap<>();

    private final AreaInformationHolder areaInformationHolder = new AreaInformationHolder();

    // TODO: Bean 으로 등록해야만 ProductScore 와의 의존성 해결
    private final ProductClassificationInformationHolder productClassificationInformationHolder
            = new ProductClassificationInformationHolder();

    public ProductScoreStatistics() throws IOException, ParseException {
        init();
    }

    private void init(){
        ArrayList<String> areaList = areaInformationHolder.getAreaList();

        // 지역별로 스코어 초기화
        for(String area : areaList){
            areaProductLargeScore.put(area, new HashMap<>());
            areaProductMidScore.put(area, new HashMap<>());

            ArrayList<String> unitySignguList =
                    areaInformationHolder.getUnitySignguListByArea(area);

            // 지역의 시군구 별로 스코어 초기화
            for(String unitySigngu : unitySignguList){
                areaProductLargeScore.get(area)
                        .put(unitySigngu,new ProductScore(productClassificationInformationHolder));
                areaProductMidScore.get(area)
                        .put(unitySigngu,new ProductScore(productClassificationInformationHolder));
            }
        }
    }

    public HashMap getAreaProductLargeScore(String area){
        if(!areaProductLargeScore.containsKey(area)){
            throw new RuntimeException("area not found: "+area);
        }

        return areaProductLargeScore.get(area);
    }

    public HashMap getAreaProductMidScore(String area){
        if(!areaProductMidScore.containsKey(area)){
            throw new RuntimeException("area not found: "+area);
        }

        return areaProductMidScore.get(area);
    }

    public ProductScore getProductMidScoreByUnity(String area, String unitysigngu){
        HashMap areaProductMidScoreMap = getAreaProductMidScore(area);

        if(!areaProductMidScoreMap.containsKey(area)){
            throw new RuntimeException("unity not found: "+unitysigngu);
        }

        return (ProductScore) areaProductMidScoreMap.get(unitysigngu);
    }

    public ProductScore getProductLargeScoreByUnity(String area, String unitysigngu){
        HashMap areaProductLargeScoreMap = getAreaProductLargeScore(area);

        if(!areaProductLargeScoreMap.containsKey(area)){
            throw new RuntimeException("unity not found: "+unitysigngu);
        }

        return (ProductScore) areaProductLargeScoreMap.get(unitysigngu);
    }

    public void updateProductLargeScoreByUnity(String area, String unitysigngu, String productClassficationint, int score){
        // 지역에 해당하는 시군구 중 하나에 대한 product score 업데이트
        ProductScore updateProductLargeScoreByUnity = getProductLargeScoreByUnity(area, unitysigngu);
        updateProductLargeScoreByUnity.update(productClassficationint, score);
    }

    public void updateProductMidScoreByUnity(String area, String unitysigngu, String productClassficationint, int score){
        // 지역에 해당하는 시군구 중 하나에 대한 product score 업데이트
        ProductScore updateProductMidScoreByUnity = getProductMidScoreByUnity(area, unitysigngu);
        updateProductMidScoreByUnity.update(productClassficationint, score);
    }

    public HashMap<String,HashMap<String, Integer>>  getUnityTotalScoreForLargeClassificationByArea(String area){
        // 한 지역에 대한 대분류 총점 스코어 가져오기
        // [{"unity": "유성구","list" : [{"classification":"가공식품", "qauntity" : 0}, ... ], ... ]

        // 시군구 별 스코어 가져오기
        ArrayList<String> unityList = areaInformationHolder.getUnitySignguListByArea(area);
        HashMap<String, ProductScore> scoreByUnity = areaProductLargeScore.get(area);
        HashMap<String,HashMap<String, Integer>> totalScoreByUnity = new HashMap<>();

        // 시군구 별 ProductScore를 가져온 다음에 해당 시군구에 대한 LargeScoreMapper(물품, 물품 수)를 가져옴
        unityList.forEach(unity ->
                totalScoreByUnity.put(unity,scoreByUnity.get(unity).getProductLargeScoreMapper()));

        // 각 시군구 별 스코어 현황 가져옴(시군구, (물품, 물품 수))
        return totalScoreByUnity;
    }

    public HashMap getUnityTotalScoreForMidClassificationByArea(String area){
        // 한 지역에 대한 중분류 총점 스코어 가져오기
        // [{"unity": "유성구","list" : [{"classification":"가공식품", "qauntity" : 0}, ... ], ... ]

        // 시군구 별 스코어 가져오기
        ArrayList<String> unityList = areaInformationHolder.getUnitySignguListByArea(area);
        HashMap<String, ProductScore> scoreByUnity = areaProductMidScore.get(area);
        HashMap<String,HashMap<String, Integer>> totalScoreByUnity = new HashMap<>();

        // 시군구 별 ProductScore를 가져온 다음에 해당 시군구에 대한 MidScoreMapper(물품, 물품 수)를 가져옴
        unityList.forEach(unity ->
                totalScoreByUnity.put(unity,scoreByUnity.get(unity).getProductMidScoreMapper()));

        // 각 시군구 별 스코어 현황 가져옴(시군구, (물품, 물품 수))
        return totalScoreByUnity;
    }
}


@Setter
@Getter
class ProductScore{
    HashMap<String,Integer> productLargeScoreMapper = new HashMap<>();
    HashMap<String,Integer> productMidScoreMapper = new HashMap<>();
    ProductClassificationInformationHolder productClassificationInformationHolder;

    ProductScore(ProductClassificationInformationHolder holder){
        this.productClassificationInformationHolder = holder;
    }

    private void init(){
        ArrayList<String> prMidList =
                productClassificationInformationHolder.getProductMidClassificationList();
        ArrayList<String> prLargeList =
                productClassificationInformationHolder.getProductLargeClassificationList();


        // 각 물품분류에 대한 갯수 초기화
        prMidList.forEach(product -> productMidScoreMapper.put(product,0));
        prLargeList.forEach(product -> productLargeScoreMapper.put(product,0));
    }

    public void update(String productClassification, int score){
        HashMap<String,Integer> scoreMapper;

        // Mapper 고르기
        if(productMidScoreMapper.containsKey(productClassification)){
            scoreMapper = productMidScoreMapper;
        }else{
            scoreMapper = productLargeScoreMapper;
        }

        // 예외처리
        if(!scoreMapper.containsKey(productClassification)){
            throw new RuntimeException("not found classification: "+productClassification);
        }

        int prevScore = scoreMapper.get(productClassification);
        scoreMapper.put(productClassification,prevScore+score);
    }
}


