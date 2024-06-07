package com.choiteresa.fonation.domain.product_donation_form.controller;

import com.choiteresa.fonation.domain.product.entity.Product;
import com.choiteresa.fonation.domain.product_donation_form.Dto.DonationFormRequest;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import com.choiteresa.fonation.domain.product_donation_form.service.ProductDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductDonationController {
    private final ProductDonationService service;
    @Autowired
    public ProductDonationController(ProductDonationService service) {
        this.service = service;
    }

    @GetMapping("/api/donationForms")
    public List<ProductDonationForm> getAllDonationForms() {
        // TODO : 도네이션 폼을 전부 얻어오는 API
        return service.getAllDonationForms();
    }

    @PostMapping("/donationForm")
    public ResponseEntity<ProductDonationForm> submitDonationForm(@RequestBody DonationFormRequest request) {
        ProductDonationForm form = new ProductDonationForm(request.getDonationUserId());
        List<Product> products = request.getProductList().stream()
                .map(p -> new Product(p.getCategory(), p.getName(), p.getQuantity(), p.getExpireDate(),p.getStoreType(), p.getWeight(),p.getIsSelected()))
                .collect(Collectors.toList());
        for (Product product : products) {
            product.setDonationForm(form);
        }

        form.setProducts(products);
        ProductDonationForm savedForm = service.submitDonationForm(form);
        return ResponseEntity.ok(savedForm);
    }
}
