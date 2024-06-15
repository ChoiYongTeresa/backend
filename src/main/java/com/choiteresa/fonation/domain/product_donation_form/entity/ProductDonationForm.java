package com.choiteresa.fonation.domain.product_donation_form.entity;

import com.choiteresa.fonation.domain.foodmarket_product_donation_form.entity.FoodmarketProductRelation;
import com.choiteresa.fonation.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "product_donation_form")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"relations"})
public class ProductDonationForm {
    //id, donation_user_id, is_selected
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, unique = true)
    private Long id;

    //User one : ProductDonationForm many
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member donationUser; // donationUserId였는데 변경됨.

    @OneToMany(mappedBy = "donationForm")
    private List<FoodmarketProductRelation> relations;


    @Column(nullable = false)
    private boolean isSelected = false;


//    private LocalDateTime createdAt;
//
//    private LocalDateTime updatedAt;

    public ProductDonationForm(Member donationUserId) {
        this.donationUser = donationUserId;
    }

    public void setIsSelected(boolean b) {
        isSelected = b;
    }



}

