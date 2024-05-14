package com.choiteresa.fonation.domain.foodmarket.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceFoodApiRequestDto {
    String serviceKey; // 인증키
    String numOfRows; // 한 페이지 결과 수
    String pageNo; // 페이지 번호
    String dataType; // 응답자료형식
    String areaCd; // 지역 코드
    String unitySignguCd; // 통합시군구코드
    String spctrCd; // 지원센터코드
    String preferCnttgClscd; // 선호물품분류코드
}
