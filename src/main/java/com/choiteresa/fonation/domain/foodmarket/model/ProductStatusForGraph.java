package com.choiteresa.fonation.domain.foodmarket.model;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductStatusForGraph {
    // [{"unity": "유성구","list" : [{"classification":"가공식품", "qauntity" : 0}, ... ], ... ]
    // 해당 지역의 시군구 별 스코어 현황 가져옴 ex. [유성구, (["가공식품",0].["유제품", 10])]

    String cls;
    Integer count;
}
