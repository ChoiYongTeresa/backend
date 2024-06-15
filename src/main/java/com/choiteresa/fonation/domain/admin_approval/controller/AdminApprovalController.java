package com.choiteresa.fonation.domain.admin_approval.controller;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import com.choiteresa.fonation.domain.product_donation_form.Dto.ProductInfoDTO;
import com.choiteresa.fonation.domain.product_donation_form.service.ProductDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/donations")
public class AdminApprovalController {
    @Autowired
    private ProductDonationService productDonationService;

    @Autowired
    public AdminApprovalController(ProductDonationService productDonationService) {
        this.productDonationService = productDonationService;
    }
//    @GetMapping("/requestlist")
//    public ResponseEntity<?> fetchDonationRequests() {
//        try {
//            List<DonationRequestDto> donationRequests = adminApprovalService.fetchAllDonationRequests();
//            return ResponseEntity.ok(donationRequests);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//
//        }
//    }]
    @GetMapping("/{donationId}")
    public ResponseEntity<?> getDonationDetails(@PathVariable("donationId") Long id) {
        List<ProductInfoDTO> productDetails = productDonationService.getProductDetailsByDonationId(id);
        return ResponseEntity.ok(productDetails);
//        try {
//            ProductDonationForm form = productDonationService.getForm(id);
//            return ResponseEntity.ok(form);
//        } catch (Exception e) {
//            System.err.println("Error retrieving donation form: " + e.getMessage());
//            e.printStackTrace(); // 서버 로그에 출력
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error retrieving donation form");
//        }
    }

    @PostMapping("/{donationId}/approval")
    public ResponseEntity<FoodmarketProductRelation> approveForm(@PathVariable("donationId") Long id) {
    // TODO : 도네이션 폼을 승인하는 API
        return ResponseEntity.ok(productDonationService.approveForm(id));
    }

    @PostMapping("/{donationId}/reject")
    public ResponseEntity<FoodmarketProductRelation> rejectForm(@PathVariable("donationId") Long id) {
        // TODO : 도네이션 폼을 거절하는 API
        return ResponseEntity.ok(productDonationService.rejectForm(id));
    }

}
