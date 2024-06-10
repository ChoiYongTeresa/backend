package com.choiteresa.fonation.domain.admin_approval.controller;

import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import com.choiteresa.fonation.domain.product_donation_form.service.ProductDonationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ProductDonationForm> getForm(@PathVariable Long id) {
        // TODO : 도네이션 폼을 얻어오는 API
        return ResponseEntity.ok(productDonationService.getForm(id));
    }
    @PostMapping("/{donationId}/approval")
    public ResponseEntity<ProductDonationForm> approveForm(@PathVariable Long id) {
        return ResponseEntity.ok(productDonationService.approveForm(id));
    }

    @PostMapping("/{donationId}/reject")
    public ResponseEntity<ProductDonationForm> rejectForm(@PathVariable Long id) {

        return ResponseEntity.ok(productDonationService.rejectForm(id));
    }
}
