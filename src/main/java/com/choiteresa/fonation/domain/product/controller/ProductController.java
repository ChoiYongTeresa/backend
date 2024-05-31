package com.choiteresa.fonation.domain.product.controller;

import com.choiteresa.fonation.domain.product.service.ProductService;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donations")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/submit")
    public ResponseEntity<ProductDonationForm> submitDonationForm(@RequestBody ProductDonationForm form) {
        ProductDonationForm savedForm = productService.submitDonationForm(form);
        return ResponseEntity.ok(savedForm);
    }
}
