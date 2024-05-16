package com.choiteresa.fonation.domain.foodmarket.model;


import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PreferenceFoodApiRequestDto {
    String serviceKey; // 인증키
    String numOfRows; // 한 페이지 결과 수
    String pageNo; // 페이지 번호
    String dataType; // 응답자료형식
    String areaCd; // 지역 코드
//    String unitySignguCd; // 통합시군구코드
//    String spctrCd; // 지원센터코드
    String preferCnttgClscd; // 선호물품분류코드


    public String getParamString(){
        return "?serviceKey=" + getServiceKey() +
                "&numOfRows=" + getNumOfRows() +
                "&pageNo=" + getPageNo() +
                "&dataType=" + getDataType() +
                "&areaCd=" + getAreaCd() +
                "&preferCnttgClscd=" + getPreferCnttgClscd();
    }
}
