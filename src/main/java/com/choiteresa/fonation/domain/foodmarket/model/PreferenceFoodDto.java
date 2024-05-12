package com.choiteresa.fonation.domain.foodmarket.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PreferenceFoodDto{
    String areaCd; // 인증키
    String unitySignguCd; // 한 페이지 결과 수
    String spctrCd; // 페이지 번호
    String preferCnttgClscd; // 응답자료형식
    String holdQy; // 지역 코드
}