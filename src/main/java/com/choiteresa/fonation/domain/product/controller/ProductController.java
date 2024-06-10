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
@RequestMapping("/donations/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/donationForm")
    public ResponseEntity<ProductDonationForm> submitDonationForm(@RequestBody ProductDonationForm form) {
        // TODO : donationForm을 제출하는 API
        ProductDonationForm savedForm = productService.submitDonationForm(form);
        return ResponseEntity.ok(savedForm);
    }
}
