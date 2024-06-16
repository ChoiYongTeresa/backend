package com.choiteresa.fonation.domain.product_donation_form.service;

import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.foodmarket.repository.FoodMarketRepository;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.dto.FoodmarketProductRelationDTO;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.repository.FPRepository;
import com.choiteresa.fonation.domain.member.entity.Member;
import com.choiteresa.fonation.domain.member.repository.MemberRepository;
import com.choiteresa.fonation.domain.product.entity.Product;
import com.choiteresa.fonation.domain.product.repository.ProductRepository;
import com.choiteresa.fonation.domain.product_donation_form.Dto.DonationFormRequest;
import com.choiteresa.fonation.domain.product_donation_form.Dto.DonationResponse;
import com.choiteresa.fonation.domain.product_donation_form.Dto.ProductInfoDTO;
import com.choiteresa.fonation.domain.product_donation_form.Dto.ProductRequest;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import com.choiteresa.fonation.domain.product_donation_form.repository.ProductDonationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDonationService {
    private final ProductDonationRepository productDonationRepository;
    @Autowired
    private FPRepository foodmarketProductRelationRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FoodMarketRepository foodMarketRepository;
    @Autowired
    public ProductDonationService(ProductDonationRepository productDonationRepository, FPRepository foodmarketProductRelationRepository) {
        this.productDonationRepository = productDonationRepository;
        this.foodmarketProductRelationRepository = foodmarketProductRelationRepository;
    }
    public List<ProductDonationForm> getAllDonationForms() {
        return productDonationRepository.findAll();
    }
    public ProductDonationForm submitDonationForm(ProductDonationForm form) {
        return productDonationRepository.save(form);
    }
    @Transactional
    public DonationResponse createDonation(DonationFormRequest request) {
        if (request.getMemberId() == null) {
            throw new IllegalArgumentException("Member ID must not be null");
        }
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        ProductDonationForm form = new ProductDonationForm(member);
        form.setRelations(new ArrayList<>());
        productDonationRepository.save(form);
        List<Long> productIds = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        if (request.getFoodMarketList() == null || request.getFoodMarketList().isEmpty()) {
            throw new IllegalArgumentException("Food market list must not be empty");
        }

        if (request.getFoodMarketList() == null) {
            throw new IllegalArgumentException("Food market list must not be null");
        }
        for (Long marketId : request.getFoodMarketList()) {
            FoodMarket foodMarket = foodMarketRepository.findById(marketId)
                    .orElseThrow(() -> new IllegalArgumentException("FoodMarket not found"));

            FoodmarketProductRelation relation = new FoodmarketProductRelation(form, foodMarket);
//            relation.setProducts(products);
//            form.getRelations().add(relation);
//            relation.setDonationForm(form);
            relation.setFoodMarket(foodMarket);
            foodmarketProductRelationRepository.save(relation);
            for (ProductRequest productReq : request.getProductList()) {
                Product product = new Product(productReq.getCategory(), productReq.getName(), productReq.getQuantity(), productReq.getExpireDate(), productReq.getStoreType(), productReq.getWeight(), productReq.getIsSelected());
                product.setRelation(relation);
                productRepository.save(product);
                productIds.add(product.getId());
            }
        }
        return new DonationResponse(form.getId(), productIds);
    }
    @Transactional
    public FoodmarketProductRelationDTO approveForm(Long id) {
        FoodmarketProductRelation relation = foodmarketProductRelationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FoodmarketProductRelation not found"));
        relation.setApprovedDate(new Date());
        FoodmarketProductRelation savedRelation = foodmarketProductRelationRepository.save(relation);
        return new FoodmarketProductRelationDTO(savedRelation.getId(), savedRelation.getApprovedDate(), savedRelation.getFoodMarket().getName());
    }
    @Transactional
    public FoodmarketProductRelationDTO rejectForm(Long id) {
        FoodmarketProductRelation relation = foodmarketProductRelationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FoodmarketProductRelation not found"));
        relation.setApprovedDate(new Date());
        FoodmarketProductRelation savedRelation = foodmarketProductRelationRepository.save(relation);
        return new FoodmarketProductRelationDTO(savedRelation.getId(), savedRelation.getApprovedDate(), savedRelation.getFoodMarket().getName());
    }

    public List<ProductInfoDTO> getProductDetailsByDonationId(Long donationId, Long foodMarketId) {
        // TODO : 도네이션 폼에 속한 제품들의 정보를 가져오는 API
        ProductDonationForm form = productDonationRepository.findById(donationId)
                .orElseThrow(() -> new IllegalArgumentException("ProductDonationForm not found"));
        return form.getRelations().stream()
                .filter(relation -> relation.getFoodMarket().getId().equals(foodMarketId))
                .flatMap(relation -> relation.getProducts().stream()
                .map(product -> new ProductInfoDTO(
                form.getDonationUser().getMemberName(),
                form.getDonationUser().getPhoneNumber(),
                form.getDonationUser().getEmail(),
                product.getCategory(),
                product.getName(),
                product.getQuantity(),
                product.getExpireDate(),
                product.getStoreType(),
                relation.getId()
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
