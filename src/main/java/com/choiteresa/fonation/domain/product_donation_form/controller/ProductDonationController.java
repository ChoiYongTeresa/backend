package com.choiteresa.fonation.domain.product_donation_form.controller;

import com.choiteresa.fonation.domain.product_donation_form.Dto.DonationFormRequest;
import com.choiteresa.fonation.domain.product_donation_form.Dto.DonationResponse;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import com.choiteresa.fonation.domain.product_donation_form.service.ProductDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductDonationController {
    private final ProductDonationService productDonationService;
    @Autowired
    public ProductDonationController(ProductDonationService service) {
        this.productDonationService = service;
    }

    @GetMapping("/api/donationForms")
    public List<ProductDonationForm> getAllDonationForms() {
        // TODO : 도네이션 폼을 전부 얻어오는 API
        return productDonationService.getAllDonationForms();
    }
    @PostMapping("/donations/product/donationForm")
    public ResponseEntity<?> submitDonationForm(@RequestBody DonationFormRequest request) {
        // TODO : 도네이션 폼을 제출하는 API
        DonationResponse response = productDonationService.createDonation(request);
        return ResponseEntity.ok(response);
    }
}
