package com.choiteresa.fonation.domain.foodmarket_product_donation_form.repository;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FPRepository extends JpaRepository<FoodmarketProductRelation, Long>{
//    List<FoodmarketProductRelation> findByFoodmarketId(Long foodmarketId);
//    List<FoodmarketProductRelation> findByDonationFormId(Long donationFormId);
}

