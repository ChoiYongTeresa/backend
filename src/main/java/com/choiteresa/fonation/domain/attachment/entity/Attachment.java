package com.choiteresa.fonation.domain.attachment.entity;

import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import jakarta.persistence.*;

@Entity
public class Attachment {
    // id donation_form_id type form
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private Long id;

    // donation_form_id = one , attachment = many
    @ManyToOne
    @JoinColumn(name = "donation_form_id")
    private ProductDonationForm donationFormId;

    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private String form;
}
