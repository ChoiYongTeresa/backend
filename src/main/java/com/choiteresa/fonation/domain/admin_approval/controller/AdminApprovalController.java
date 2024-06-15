package com.choiteresa.fonation.domain.admin_approval.controller;

import com.choiteresa.fonation.domain.admin_approval.Dto.DonationFormDTO;
import com.choiteresa.fonation.domain.admin_approval.service.AdminApprovalService;
import com.choiteresa.fonation.domain.foodmarket_product_donation_form.dto.FoodmarketProductRelationDTO;
import com.choiteresa.fonation.domain.product_donation_form.Dto.ProductInfoDTO;
import com.choiteresa.fonation.domain.product_donation_form.service.ProductDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/donations")
public class AdminApprovalController {
    @Autowired
    private ProductDonationService productDonationService;
    @Autowired
    private AdminApprovalService adminApprovalService;

    @Autowired
    public AdminApprovalController(ProductDonationService productDonationService) {
        this.productDonationService = productDonationService;
    }
    @GetMapping("/requestList")
    public ResponseEntity<?> getDonationFormsByFoodMarket(@RequestParam Long foodMarketId) {
        // TODO : 해당 푸드마켓의 도네이션 폼 목록을 가져오는 API
        try {
            List<DonationFormDTO> donationForms = adminApprovalService.getAllDonationFormsForFoodMarket(foodMarketId);
            return ResponseEntity.ok(donationForms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve donation forms: " + e.getMessage());
        }
    }
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
    public ResponseEntity<FoodmarketProductRelationDTO> approveForm(@PathVariable("donationId") Long id) {
    // TODO : 도네이션 폼을 승인하는 API
        return ResponseEntity.ok(productDonationService.approveForm(id));
    }

    @PostMapping("/{donationId}/reject")
    public ResponseEntity<FoodmarketProductRelationDTO> rejectForm(@PathVariable("donationId") Long id) {
        // TODO : 도네이션 폼을 거절하는 API
        return ResponseEntity.ok(productDonationService.rejectForm(id));
    }

}
