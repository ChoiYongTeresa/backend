package com.choiteresa.fonation.domain.product_donation_form.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DonationFormRequest {
    private Long donationUserId;
    private List<ProductRequest> productList;
}
