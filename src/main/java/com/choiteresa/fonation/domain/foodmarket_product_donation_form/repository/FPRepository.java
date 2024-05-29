package com.choiteresa.fonation.domain.foodmarket_product_donation_form.repository;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FPRepository extends JpaRepository<FoodmarketProductRelation,Long> {

    Optional<List<FoodmarketProductRelation>> findByDonationForm(ProductDonationForm donationForm);
}
