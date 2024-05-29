package com.choiteresa.fonation.domain.product_donation_form.entity;

import com.choiteresa.fonation.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product_donation_form")
@NoArgsConstructor
@AllArgsConstructor
public class ProductDonationForm {
    //id, donation_user_id, is_selected
    @Id
    @GeneratedValue
    @Column(name="id", nullable = false, unique = true)
    private Long id;

    //User one : ProductDonationForm many
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member donationUser;

    @Column(nullable = false)
    private boolean isSelected;


}
