package com.choiteresa.fonation.domain.product_donation_form.service;

import com.choiteresa.fonation.domain.foodmarket.repository.FoodMarketRepository;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.repository.FPRepository;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import com.choiteresa.fonation.domain.product_donation_form.repository.ProductDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductDonationService {
    @Autowired
    private FoodMarketRepository foodMarketRepository;
    @Autowired
    private FPRepository foodMarketProductRepository;
    @Autowired
    private ProductDonationRepository productDonationRepository;

    public ProductDonationForm submitDonationForm (ProductDonationForm form) {
        form.setStatus("WAITING");
        return productDonationRepository.save(form);
    }

    public List<ProductDonationForm> getAllWaitingForms() {
        return productDonationRepository.findByStatus("WAITING");
    }
    public ProductDonationForm approveForm(@PathVariable Long id) {
        ProductDonationForm form = productDonationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("�ش� ��� ��û�� �������� �ʽ��ϴ�."));
        form.setStatus("APPROVED");
        form.setIsSelected(true);
        form.setUpdatedAt(LocalDateTime.now());
        return productDonationRepository.save(form);
    }

    public ProductDonationForm rejectForm(@PathVariable Long id) {
        ProductDonationForm form = productDonationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("�ش� ��� ��û�� �������� �ʽ��ϴ�."));
        form.setStatus("REJECTED");
        form.setIsSelected(false);
        form.setUpdatedAt(LocalDateTime.now());
        return productDonationRepository.save(form);
    }

//    @Transactional
//    public FoodMarket banProduct(Long foodMarketId, String prohibitedItem){
//        FoodMarket foodMarket = foodMarketRepository.findById(foodMarketId)
//                .orElseThrow(() -> new IllegalArgumentException("FoodMarket not found"));
//        Product product = productRepository.findById
//    }
}
