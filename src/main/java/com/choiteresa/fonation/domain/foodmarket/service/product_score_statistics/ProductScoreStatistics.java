package com.choiteresa.fonation.domain.foodmarket.service.product_score_statistics;

import com.choiteresa.fonation.domain.foodmarket.service.information_holder.AreaInformationHolder;
import com.choiteresa.fonation.domain.foodmarket.service.information_holder.ProductClassificationInformationHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class ProductScoreStatistics {
    private ArrayList<AreaWithUnityProductScore> areaProductScore = new ArrayList<>();

    private AreaInformationHolder areaInformationHolder  = new AreaInformationHolder();

    // TODO: Bean 으로 등록해야만 ProductScore 와의 의존성 해결
    private ProductClassificationInformationHolder productClassificationInformationHolder
            = new ProductClassificationInformationHolder();

    public ProductScoreStatistics() {
        init();
    }

    private void init(){
        List<String> areaList = areaInformationHolder.getAreaList();

        // 지역별로 스코어 초기화
        for(String area : areaList){
            areaProductScore.add(new AreaWithUnityProductScore(area,new ArrayList<>()));

            ArrayList<String> unityList =
                    areaInformationHolder.getUnityByArea(area);

            // 지역의 시군구 별로 스코어 초기화
            for(String unity : unityList){
                AreaWithUnityProductScore unityProductScore =
                        findUnityProductScoreWithArea(area);

                unityProductScore.getUnityWithProductScoreList()
                        .add(new UnityWithProductScore(unity,new ProductScore(productClassificationInformationHolder)));
            }
        }
    }

    public AreaWithUnityProductScore findUnityProductScoreWithArea(String area){
        // 지역을 기준으로 시군구에 대한 분류-스코어 해시맵 가져오기
        return this.areaProductScore.stream()
                .filter(object->object.getArea().equals(area))
                .findFirst()
                .orElseThrow(()->new RuntimeException("area not found: "+area));
    }

    public UnityWithProductScore findProductScoreWithUnity(String area, String unity){
        AreaWithUnityProductScore unityProductScore= findUnityProductScoreWithArea(area);

        return unityProductScore.getUnityWithProductScoreList().stream()
                .filter(object->object.getUnity().equals(unity))
                .findFirst()
                .orElseThrow(()->new RuntimeException("unity not found: "+unity));
    }

    public void updateProductLargeScoreByUnity(String area, String unity, String productClassfication, int score){
        // 지역에 해당하는 시군구 중 하나에 대한 product score 업데이트

        // 시군구에 해당하는 unity-productScore 쌍 가져오기
        UnityWithProductScore productScoreWithUnity = findProductScoreWithUnity(area, unity);

        // 해당 시군구의 productScore 가져오기
        ProductScore productScore = productScoreWithUnity.getProductScore();

        // 대분류에 대한 스코어 업데이트
        productScore.updateLarge(productClassfication, score);
    }

    public void updateProductMidScoreByUnity(String area, String unity, String productClassfication, int score){
        // 지역에 해당하는 시군구 중 하나에 대한 product score 업데이트

        // 시군구에 해당하는 unity-productScore 쌍 가져오기
        UnityWithProductScore productScoreWithUnity = findProductScoreWithUnity(area, unity);

        // 해당 시군구의 productScore 가져오기
        ProductScore productScore = productScoreWithUnity.getProductScore();

        // 대분류에 대한 스코어 업데이트
        productScore.updateMid(productClassfication, score);
    }

    public ArrayList<UnityWithProductScore>  getUnityTotalScoreForLargeClassificationByArea(String area){
        // 한 지역에 대한 대분류 총점 스코어 가져오기

        // 시군구 별 스코어 가져오기
        AreaWithUnityProductScore unityProductScoreWithArea
                = findUnityProductScoreWithArea(area);

       return unityProductScoreWithArea.getUnityWithProductScoreList();
    }
}


