package com.choiteresa.fonation.domain.product_donation_form.Dto;

import com.choiteresa.fonation.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DonationFormRequest {
    private Member donationUserId;
    private List<ProductRequest> productList;
    private List<Integer> foodMarketIds;



    @Getter
    public static class ProductRequest {
        private String category;
        private String name;
        private int quantity;
        private String expireDate;
        private int storeType;
        private int weight;
        private boolean isSelected;

    }
}
