package com.choiteresa.fonation.domain.product_donation_form.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductInfoDTO {
    private String name;
    private String phone;
    private String email;
    private String productCategory;
    private String productName;
    private int productNum;
    private Date expireDate;
    private int productStorage;
    private Long relationId;

    public ProductInfoDTO(String name, String phone, String email, String productCategory, String productName, int productNum, Date expireDate, int productStorage, Long relationId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.productCategory = productCategory;
        this.productName = productName;
        this.productNum = productNum;
        this.expireDate = expireDate;
        this.productStorage = productStorage;
        this.relationId = relationId;

    }
//    private Long donationId;
//    private String userId;
//    private Date approvedDate;
//
//    public ProductInfoDTO(Long donationId, String userId, Date approvedDate) {
//        this.donationId = donationId;
//        this.userId = userId;
//        this.approvedDate = approvedDate;
//    }
//    public Long getDonationId() {
//        return donationId;
//    }
//    public void setDonationId(Long donationId) {
//        this.donationId = donationId;
//    }
//    public String getUserId() {
//        return userId;
//    }
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//    public Date getApprovedDate() {
//        return approvedDate;
//    }
//    public void setApprovedDate(Date approvedDate) {
//        this.approvedDate = approvedDate;
//    }
}
