package com.choiteresa.fonation.domain.admin_approval.entity;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class AdminApproval {
    //id, relation_id, is_approved, approved_at
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    @JoinColumn(name = "relation_id")
    private FoodmarketProductRelation relationId;

    @Column(nullable = true)
    private Date isApproved;

//    @Column(nullable = false)
//    private Date approvedAt;
}
