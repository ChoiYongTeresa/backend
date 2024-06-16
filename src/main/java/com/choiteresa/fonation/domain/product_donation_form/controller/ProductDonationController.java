package com.choiteresa.fonation.domain.product_donation_form.controller;

import com.choiteresa.fonation.domain.product_donation_form.service.ProductDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductDonationController {
    private final ProductDonationService productDonationService;
    @Autowired
    public ProductDonationController(ProductDonationService service) {
        this.productDonationService = service;
    }

}
