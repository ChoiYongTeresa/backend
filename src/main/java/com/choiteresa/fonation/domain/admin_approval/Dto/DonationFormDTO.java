package com.choiteresa.fonation.domain.admin_approval.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DonationFormDTO {
    private Long donationFormId;
    private String foodMarketName;
    private String donorName;
    private String selectedDate;  // null
    private String approvedDate;  // null

    public DonationFormDTO(Long donationFormId, String foodMarketName, String donorName, Date selectedDate, Date approvedDate) {
        this.donationFormId = donationFormId;
        this.foodMarketName = foodMarketName;
        this.donorName = donorName;
        this.selectedDate = selectedDate != null ? selectedDate.toString() : "";
        this.approvedDate = approvedDate != null ? approvedDate.toString() : "";
    }
}
