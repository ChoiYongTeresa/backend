package com.choiteresa.fonation.domain.admin_approval.service;

import com.choiteresa.fonation.domain.admin_approval.Dto.DonationFormDTO;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.repository.FPRepository;
import com.choiteresa.fonation.domain.product_donation_form.repository.ProductDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminApprovalService {
    @Autowired
    private ProductDonationRepository productDonationRepository;
    @Autowired
    private FPRepository foodmarketProductRelationRepository;
    public List<DonationFormDTO> getAllDonationFormsForFoodMarket(Long foodMarketId) {
        List<FoodmarketProductRelation> relations = foodmarketProductRelationRepository.findByFoodMarketId(foodMarketId);
        return relations.stream()
                .map(relation -> new DonationFormDTO(
                        relation.getDonationForm().getId(),
                        relation.getFoodMarket() != null ? relation.getFoodMarket().getName() : "Unknown Market",
                        relation.getDonationForm().getDonationUser().getMemberName(),
                        relation.getSelectedDate(),
                        relation.getApprovedDate()
                ))
                .collect(Collectors.toList());
    }
}
