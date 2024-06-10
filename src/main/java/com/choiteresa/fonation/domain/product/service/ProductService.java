package com.choiteresa.fonation.domain.product.service;

import com.choiteresa.fonation.domain.product.entity.Product;
import com.choiteresa.fonation.domain.product.repository.ProductRepository;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import com.choiteresa.fonation.domain.product_donation_form.repository.ProductDonationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDonationRepository formRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public List<Product> saveAllProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }


    public ProductDonationForm submitDonationForm(ProductDonationForm form) {
        // TODO : ?? ???? ??, ??? ???? ??????? ????? ???? ??????.
        form.setStatus("WAITING");
        return formRepository.save(form);
    }
}
