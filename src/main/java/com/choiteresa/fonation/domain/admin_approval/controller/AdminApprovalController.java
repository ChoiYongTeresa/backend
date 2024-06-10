package com.choiteresa.fonation.domain.admin_approval.controller;

import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import com.choiteresa.fonation.domain.product_donation_form.service.ProductDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/donations")
public class AdminApprovalController {
    @Autowired
    private ProductDonationService productDonationService;

//    @GetMapping("/requestlist")
//    public ResponseEntity<?> fetchDonationRequests() {
//        try {
//            List<DonationRequestDto> donationRequests = adminApprovalService.fetchAllDonationRequests();
//            return ResponseEntity.ok(donationRequests);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//
//        }
//    }
//    @GetMapping("/admin/donations/requestList")
//    public ResponseEntity<List<ProductDonationForm>> listWaitingForms() {
//        return ResponseEntity.ok(productDonationService.getAllWaitingForms());
//    }
    @Autowired
    public AdminApprovalController(ProductDonationService productDonationService) {
        this.productDonationService = productDonationService;
    }
    @GetMapping("/{donationId}")
    public ResponseEntity<?> getForm(@PathVariable("donationId") Long id) {
        try {
            ProductDonationForm form = productDonationService.getForm(id);
            return ResponseEntity.ok(form);
        } catch (Exception e) {
            // 로깅을 추가하여 오류의 원인을 추적할 수 있도록 합니다.
            // 예를 들어, 로그에 오류 정보를 출력
            System.err.println("Error retrieving donation form: " + e.getMessage());
            e.printStackTrace(); // 서버 로그에 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving donation form");
        }
    }

//    @PostMapping("/{donationId}/approval")
//    public ResponseEntity<ProductDonationForm> approveForm(@PathVariable Long id) {
//        // TODO : 도네이션 폼을 승인하는 API
//        return ResponseEntity.ok(productDonationService.approveForm(id));
//    }
//
//    @PostMapping("/{donationId}/reject")
//    public ResponseEntity<ProductDonationForm> rejectForm(@PathVariable Long id) {
//        // TODO : 도네이션 폼을 거절하는 API
//        return ResponseEntity.ok(productDonationService.rejectForm(id));
//    }
    @PostMapping("/{donationId}/approval")
    public ResponseEntity<ProductDonationForm> approveForm(@PathVariable("donationId") Long id) {
    // TODO : 도네이션 폼을 승인하는 API
        return ResponseEntity.ok(productDonationService.approveForm(id));
    }

    @PostMapping("/{donationId}/reject")
    public ResponseEntity<ProductDonationForm> rejectForm(@PathVariable("donationId") Long id) {
        // TODO : 도네이션 폼을 거절하는 API
        return ResponseEntity.ok(productDonationService.rejectForm(id));
    }

}
