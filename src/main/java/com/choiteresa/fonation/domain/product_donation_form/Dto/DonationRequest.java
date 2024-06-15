package com.choiteresa.fonation.domain.product_donation_form.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DonationRequest {
    // 기부 신청 시 사용하는 DTO
    private String id;
    private String name;
    private String phone;
    private String email;
    private List<ProductRequest> productList;
    private List<Integer> foodmarketList;
}
