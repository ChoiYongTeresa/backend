package com.choiteresa.fonation.domain.product_donation_form.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DonationFormRequest {
    // 기부 신청 시 사용하는 DTO
    private String memberId;
    private List<ProductRequest> productList;
    private List<Long> foodMarketList;
}
