package com.choiteresa.fonation.domain.product.service;

import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import com.choiteresa.fonation.domain.product_donation_form.repository.ProductDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductDonationRepository formRepository;

    public ProductDonationForm submitDonationForm(ProductDonationForm form) {
        form.setStatus("WAITING");
        return formRepository.save(form);
    }
}
