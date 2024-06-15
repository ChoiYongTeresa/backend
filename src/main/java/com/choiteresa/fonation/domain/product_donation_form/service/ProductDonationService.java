package com.choiteresa.fonation.domain.product_donation_form.service;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.repository.FPRepository;
import com.choiteresa.fonation.domain.member.entity.Member;
import com.choiteresa.fonation.domain.member.repository.MemberRepository;
import com.choiteresa.fonation.domain.product.repository.ProductRepository;
import com.choiteresa.fonation.domain.product_donation_form.Dto.DonationFormRequest;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import com.choiteresa.fonation.domain.product_donation_form.repository.ProductDonationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDonationService {
    private final ProductDonationRepository productDonationRepository;
    private final FPRepository foodmarketProductRelationRepository;
    private ProductRepository productRepository;
    private MemberRepository memberRepository;

    @Autowired
    public ProductDonationService(ProductDonationRepository productDonationRepository, FPRepository foodmarketProductRelationRepository) {
        this.productDonationRepository = productDonationRepository;
        this.foodmarketProductRelationRepository = foodmarketProductRelationRepository;
    }
    public List<ProductDonationForm> getAllDonationForms() {
        return productDonationRepository.findAll();
    }
    @Transactional
    public void processDonationForm(DonationFormRequest request) {
        Member member = memberRepository.findById(request.getDonationUserId().getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        ProductDonationForm form = new ProductDonationForm();
        form.setDonationUser(member);
    }
    public ProductDonationForm submitDonationForm(ProductDonationForm form) {
//        form.setStatus("WAITING");
        return productDonationRepository.save(form);
    }

    public ProductDonationForm getForm(Long id) {
        return productDonationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ProductDonationForm not found"));
    }
    @Transactional
    public ProductDonationForm approveForm(Long id) {
        ProductDonationForm form = productDonationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ProductDonationForm not found"));
//        form.setStatus("APPROVED");
        form.setIsSelected(true);
        return productDonationRepository.save(form);
    }
    @Transactional
    public ProductDonationForm rejectForm(Long id) {
        ProductDonationForm form = productDonationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ProductDonationForm not found"));
//        form.setStatus("REJECTED");
        form.setIsSelected(false);
        return productDonationRepository.save(form);
    }
//    @Transactional
//    public ProductDonationForm createDonationForm(Long userId) {
//        ProductDonationForm form = new ProductDonationForm(userId);
//        return productDonationRepository.save(form);
//    }
//
//    @Transactional
//    public FoodmarketProductRelation addFoodMarketRelation(Long formId, Long foodmarketId) {
//        ProductDonationForm form = productDonationRepository.findById(formId)
//                .orElseThrow(() -> new IllegalArgumentException("ProductDonationForm not found"));
//        FoodmarketProductRelation relation = new FoodmarketProductRelation(form, foodmarketId);
//        return foodmarketProductRelationRepository.save(relation);
//    }

}
