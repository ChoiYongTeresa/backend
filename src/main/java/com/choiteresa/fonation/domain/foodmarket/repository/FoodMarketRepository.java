package com.choiteresa.fonation.domain.foodmarket.repository;


import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodMarketRepository extends JpaRepository<FoodMarket,Integer> {
}
