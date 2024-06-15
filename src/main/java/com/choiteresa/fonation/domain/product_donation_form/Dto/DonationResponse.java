package com.choiteresa.fonation.domain.product_donation_form.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DonationResponse {
    private Long donationFormId;
    private List<Long> productIds;
    public DonationResponse(Long donationFormId, List<Long> productIds) {
        this.donationFormId = donationFormId;
        this.productIds = productIds;
    }
}
