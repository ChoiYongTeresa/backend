package com.choiteresa.fonation.domain.admin_approval.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DonationRequestDto {
    private String name;
    private String phone;
    private String email;
    private String productCategory;
    private String productName;
    private int productNum;
    private Date expireDate;
    private int productStorage;
    private String productUrl;

}
