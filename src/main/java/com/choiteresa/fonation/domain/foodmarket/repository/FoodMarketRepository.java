package com.choiteresa.fonation.domain.foodmarket.repository;


import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.foodmarket.model.FoodMarketWithDistance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface FoodMarketRepository extends JpaRepository<FoodMarket,Integer> {
    List<FoodMarket> findFoodMarketsByArea(String area);
    Optional<FoodMarket> findById(Long id);
    Optional<FoodMarket> findByNameContaining(String name);

    Optional<FoodMarket> findByCode(String code);
    // 특정 거리 이내의 푸드마켓 가져오기
    @Query( value =
            "SELECT r.id, r.latitude, r.longitude, r.area, r.unity_signgu, r.address, r.detail_address, r.name, " +
                    "r.phone_number, r.prohibited_item, " +
            "(ST_DISTANCE_SPHERE(POINT(r.longitude, r.latitude),POINT(?1, ?2))) as distance " +
            "FROM food_market r " +
            "HAVING distance<=?3 ",nativeQuery = true)
    List<FoodMarketWithDistance> findFoodMarketsInDistanceRange
            (BigDecimal givenLongitude, BigDecimal givenLatitude, BigDecimal distance);

    // 특정 지역 내 푸드마켓과의 거리를 구하고 가져오기
    @Query( value =
            "SELECT r.id, r.latitude, r.longitude, r.area, r.unity_signgu, r.address, r.detail_address, r.name, " +
                    "r.prohibited_item, r.phone_number, " +
                    "(ST_DISTANCE_SPHERE(POINT(r.longitude, r.latitude),POINT(?1, ?2))) as distance " +
                    "FROM food_market r " +
                    "WHERE r.area LIKE ?3 ",nativeQuery = true)
    List<FoodMarketWithDistance> findFoodMarketInArea
            (BigDecimal givenLongitude, BigDecimal givenLatitude, String area);
}
