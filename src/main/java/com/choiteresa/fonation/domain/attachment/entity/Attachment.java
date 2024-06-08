package com.choiteresa.fonation.domain.attachment.entity;


import com.choiteresa.fonation.domain.member.entity.Member;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;

    @Column
    public String type;

    @Column
    public String path;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    public ProductDonationForm form;

    public String getFilename(){
        String[] args = path.split("/");
        return args[args.length-1];
    }

    public void setForm(ProductDonationForm form){
        this.form = form;
    }
}
