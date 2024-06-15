package com.choiteresa.fonation.domain.product_donation_form.service;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.repository.FPRepository;
import com.choiteresa.fonation.domain.member.entity.Member;
import com.choiteresa.fonation.domain.member.repository.MemberRepository;
import com.choiteresa.fonation.domain.product.repository.ProductRepository;
import com.choiteresa.fonation.domain.product_donation_form.Dto.DonationFormRequest;
import com.choiteresa.fonation.domain.product_donation_form.Dto.ProductInfoDTO;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import com.choiteresa.fonation.domain.product_donation_form.repository.ProductDonationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDonationService {
    private final ProductDonationRepository productDonationRepository;
    @Autowired
    private FPRepository foodmarketProductRelationRepository;
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

//    public ProductDonationForm getForm(Long id) {
//        Optional<ProductDonationForm> optionalForm = productDonationRepository.findById(id);
//        if (!optionalForm.isPresent()) {
//            throw new IllegalArgumentException("ProductDonationForm not found");
//        }
//        ProductDonationForm form = optionalForm.get();
//        return new ProductInfoDTO(form.getId(), form.getDonationUser().getMemberId().toString(), form.get)
//        return productDonationRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("ProductDonationForm not found"));
//    }
    @Transactional
    public FoodmarketProductRelation approveForm(Long id) {
//        ProductDonationForm form = productDonationRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("ProductDonationForm not found"));
//        form.setIsSelected(true);
//        return productDonationRepository.save(form);
        FoodmarketProductRelation relation = foodmarketProductRelationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FoodmarketProductRelation not found"));
        relation.setApprovedDate(new Date());
        return foodmarketProductRelationRepository.save(relation);
    }
    @Transactional
    public FoodmarketProductRelation rejectForm(Long id) {
//        ProductDonationForm form = productDonationRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("ProductDonationForm not found"));
//        form.setIsSelected(false);
//        return productDonationRepository.save(form);
        FoodmarketProductRelation relation = foodmarketProductRelationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FoodmarketProductRelation not found"));
        relation.setApprovedDate(new Date());
        return foodmarketProductRelationRepository.save(relation);
    }

    public List<ProductInfoDTO> getProductDetailsByDonationId(Long donationId) {
        ProductDonationForm form = productDonationRepository.findById(donationId)
                .orElseThrow(() -> new IllegalArgumentException("ProductDonationForm not found"));
        return form.getRelations().stream()
                .flatMap(relation -> relation.getProducts().stream()
                .map(product -> new ProductInfoDTO(
                form.getDonationUser().getMemberName(),
                form.getDonationUser().getPhoneNumber(),
                form.getDonationUser().getEmail(),
                product.getCategory(),
                product.getName(),
                product.getQuantity(),
                product.getExpireDate(),
                product.getStoreType()
        ))).collect(Collectors.toList());
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
