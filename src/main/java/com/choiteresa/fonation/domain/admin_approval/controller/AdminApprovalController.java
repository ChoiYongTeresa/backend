package com.choiteresa.fonation.domain.admin_approval.controller;

import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
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
    @GetMapping("/admin/donations/requestList")
    public ResponseEntity<List<ProductDonationForm>> listWaitingForms() {
        return ResponseEntity.ok(productDonationService.getAllWaitingForms());
    }

    // 기부 신청서 확인
    @GetMapping("/admin/donations/{donationId}")
    public ResponseEntity<ProductDonationForm> getForm(@PathVariable Long id) {
        return ResponseEntity.ok(productDonationService.getForm(id));
    }

    // 수용 불가 물품 등록 (Foodmarket에 bannedProduct에 등록)
    @PostMapping("admin/donations/{foodmarketId}/banned_product") {
    public ResponseEntity<ProductDonationForm> banProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(productDonationService.banProduct(id));
    }

    @PostMapping("/admin/donations/{donationId}/approval")
    public ResponseEntity<ProductDonationForm> approveForm(@PathVariable Long id) {
        return ResponseEntity.ok(productDonationService.approveForm(id));
    }

    @PostMapping("/admin/donations/{donationId}/reject")
    public ResponseEntity<ProductDonationForm> rejectForm(@PathVariable Long id) {
        return ResponseEntity.ok(productDonationService.rejectForm(id));
    }
}
