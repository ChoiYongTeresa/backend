package com.choiteresa.fonation.domain.product_donation_form.repository;

import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDonationRepository extends JpaRepository<ProductDonationForm,Long> {
    List<ProductDonationForm> findByStatus(String status);
    Optional<ProductDonationForm> findById(Long id);
