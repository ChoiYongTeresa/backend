package com.choiteresa.fonation.domain.admin_approval.service;

import com.choiteresa.fonation.domain.admin_approval.Dto.DonationRequestDto;
import com.choiteresa.fonation.domain.product_donation_form.repository.ProductDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminApprovalService {
    @Autowired
    private ProductDonationRepository productDonationRepository;
    public List<DonationRequestDto> getAllDonationRequests() {
        return productDonationRepository.findAll().stream()
                .flatMap(form -> form.getRelations().stream())
                .flatMap(relation -> relation.getProducts().stream().map(product -> {
                    DonationRequestDto dto = new DonationRequestDto();
                    dto.setName(relation.getDonationForm().getDonationUser().getMemberName());
                    dto.setPhone(relation.getDonationForm().getDonationUser().getPhoneNumber());
                    dto.setEmail(relation.getDonationForm().getDonationUser().getEmail());
                    dto.setProductCategory(product.getCategory());
                    dto.setProductName(product.getName());
                    dto.setProductNum(product.getQuantity());
                    dto.setExpireDate(product.getExpireDate());
                    dto.setProductStorage(product.getStoreType());
                    return dto;
                }))
                .collect(Collectors.toList());
    }
}
