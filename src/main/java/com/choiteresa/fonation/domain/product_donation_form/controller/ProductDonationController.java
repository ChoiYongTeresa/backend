package com.choiteresa.fonation.domain.product_donation_form.controller;

import com.choiteresa.fonation.domain.product_donation_form.Dto.DonationFormRequest;
import com.choiteresa.fonation.domain.product_donation_form.Dto.DonationResponse;
import com.choiteresa.fonation.domain.product_donation_form.service.ProductDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductDonationController {
    private final ProductDonationService productDonationService;
    @Autowired
    public ProductDonationController(ProductDonationService service) {
        this.productDonationService = service;
    }

    @PostMapping("/donations/product/donationForm")
    public ResponseEntity<?> submitDonationForm(@RequestBody DonationFormRequest request) {
        // TODO : 도네이션 폼을 제출하는 API
        DonationResponse response = productDonationService.createDonation(request);
        return ResponseEntity.ok(response);
    }
}
