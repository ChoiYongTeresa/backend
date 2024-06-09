package com.choiteresa.fonation.domain.product_donation_form.entity;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import com.choiteresa.fonation.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class ProductDonationForm {
    //id, donation_user_id, is_selected
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, unique = true)
    private Long id;

    //User one : ProductDonationForm many
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member donationUserId;
//    private Long donationUserId;

    @OneToMany(mappedBy = "productDonationForm")
    private List<FoodmarketProductRelation> relations;

    @Column(nullable = false)
    private boolean isSelected = false;

    private String status;

//    private LocalDateTime createdAt;
//
//    private LocalDateTime updatedAt;
    public ProductDonationForm() {
    }
    public ProductDonationForm(Member donationUserId) {
        this.donationUserId = donationUserId;
    }

    public void setIsSelected(boolean b) {
        isSelected = b;
    }



}

