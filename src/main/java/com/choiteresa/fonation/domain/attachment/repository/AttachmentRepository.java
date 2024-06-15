package com.choiteresa.fonation.domain.attachment.repository;


import com.choiteresa.fonation.domain.attachment.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByProductId(Long productId);
}
