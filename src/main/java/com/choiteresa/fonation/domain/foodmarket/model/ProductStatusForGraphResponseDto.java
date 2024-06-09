package com.choiteresa.fonation.domain.foodmarket.model;


import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Getter
@Builder
public class ProductStatusForGraphResponseDto {
    String unity;
    List<ProductStatusForGraph> productList;
}


