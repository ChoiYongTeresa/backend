package com.choiteresa.fonation.domain.attachment.service;


import com.choiteresa.fonation.domain.attachment.entity.Attachment;
import com.choiteresa.fonation.domain.attachment.model.FetchAttachmentResponseDto;
import com.choiteresa.fonation.domain.attachment.model.UploadAttachmentResponseDto;
import com.choiteresa.fonation.domain.attachment.repository.AttachmentRepository;
import com.choiteresa.fonation.domain.attachment.service.attachment_holder.RandomStringGenerator;
import com.choiteresa.fonation.domain.attachment.service.enums.AttachmentFilePath;
import com.choiteresa.fonation.domain.exception.AttachmentNotFoundException;
import com.choiteresa.fonation.domain.exception.ProductNotFoundException;
import com.choiteresa.fonation.domain.product.entity.Product;
import com.choiteresa.fonation.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final ProductRepository productRepository;

    public UploadAttachmentResponseDto uploadImageAttachment(MultipartFile multipartFile, long productId) {
        // 이미지 파일 모두 업로드
        // 이미지 파일 이름 (랜덤 생성)
        String filename = RandomStringGenerator.generateRandomString(8);
        String type = "img";

        // Attachment Entity 생성
        Attachment attachment =
                Attachment.builder()
                        .type(type)
                        .path(AttachmentFilePath.IMAGE_FILE_PATH.makeFilePath(filename))
                        .build();

        // 첨부파일(multipartFile)을 디렉토리에 저장
        createImageFileData(multipartFile, attachment);

        // 기부 물품 엔티티 가져오기
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        attachment.setForm(product);

        // 첨부파일 엔티티 저장
        attachmentRepository.save(attachment);

        return UploadAttachmentResponseDto.builder()
                .filepath(attachment.getPath())
                .build();
    }


    // 상품에 해당하는 첨부파일 불러오기
    public FetchAttachmentResponseDto fetchImageAttachmentByProductId(Long productId) {
        Attachment attachment = attachmentRepository.findByProductId(productId)
                .orElseThrow(AttachmentNotFoundException::new);

        return FetchAttachmentResponseDto.builder()
                        .filepath(attachment.getPath())
                        .productId(productId)
                        .data(fetchImageFileData(attachment.getPath())).build();
    }

    public FetchAttachmentResponseDto fetchTestImage() {
        Attachment attachment = attachmentRepository.findById(2L).orElseThrow(AttachmentNotFoundException::new);

        return FetchAttachmentResponseDto.builder()
                .filepath(attachment.getPath())
                .productId(2L)
                .data(fetchImageFileData(attachment.getPath())).build();
    }

    private void createImageFileData(MultipartFile multipartFile, Attachment attachment) {
        try {
            File file = new File(AttachmentFilePath.IMAGE_FILE_PATH.getPath());
            Path path = Paths.get(file.getPath()).resolve(attachment.getFilename());

            Files.write(path, multipartFile.getBytes());
            // 업로드된 파일의 내용을 새로운 파일 내용에 복사하여 저장
        } catch (IOException e) {
            log.warn("multipartFile is not safe");
            throw new RuntimeException("multipartFile is not safe");
        }

    }

    private byte[] fetchImageFileData(String filepath) {
        byte[] bytes;
        try {
            File file = new File(filepath);
            FileImageInputStream inputStream = new FileImageInputStream(file);

            bytes = new byte[(int) file.length()];
            int offset = 0;
            int numRead;

            while (offset < bytes.length &&
                    (numRead = inputStream.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("파일을 완전히 읽지 못했습니다: " + file.getName());
            }
        } catch (IOException e) {
            throw new AttachmentNotFoundException();
        }

        return bytes;
    }
}
