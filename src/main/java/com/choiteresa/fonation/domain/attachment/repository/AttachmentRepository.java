package com.choiteresa.fonation.domain.attachment.repository;


import com.choiteresa.fonation.domain.attachment.entity.Attachment;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findAllByFormId(long form);
}
