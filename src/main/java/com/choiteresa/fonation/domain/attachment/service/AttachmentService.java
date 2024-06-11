package com.choiteresa.fonation.domain.attachment.service;


import com.choiteresa.fonation.domain.attachment.entity.Attachment;
import com.choiteresa.fonation.domain.attachment.model.FetchAttachmentResponseDto;
import com.choiteresa.fonation.domain.attachment.model.UploadAttachmentResponseDto;
import com.choiteresa.fonation.domain.attachment.repository.AttachmentRepository;
import com.choiteresa.fonation.domain.attachment.service.attachment_holder.AttachmentTracker;
import com.choiteresa.fonation.domain.attachment.service.attachment_holder.RandomStringGenerator;
import com.choiteresa.fonation.domain.attachment.service.attachment_holder.TrackingAttachmentGroup;
import com.choiteresa.fonation.domain.attachment.service.enums.AttachmentFilePath;
import com.choiteresa.fonation.domain.exception.AttachmentNotFoundException;
import com.choiteresa.fonation.domain.product_donation_form.entity.ProductDonationForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private AttachmentTracker attachmentTracker = new AttachmentTracker();

    public UploadAttachmentResponseDto uploadImageAttachmentAll(MultipartFile multipartFile, long formId) {
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

        // 기부 신청 폼 엔티티 가져오기
        ProductDonationForm donationForm = null;
        attachment.setForm(donationForm);

        // 첨부파일 엔티티 저장
        attachmentRepository.save(attachment);

        return UploadAttachmentResponseDto.builder()
                .filepath(attachment.getPath())
                .build();
    }

    // TODO: 첨부파일 업로드
    public UploadAttachmentResponseDto uploadImageAttachment(MultipartFile multipartFile, String groupId) {
        // 이미지 파일 업로드

        // 이미지 파일 이름 (랜덤 생성)
        String filename = RandomStringGenerator.generateRandomString(8);
        String type = "img";

        // Attachment Entity 생성
        Attachment attachment =
                Attachment.builder()
                        .type(type)
                        .path(AttachmentFilePath.IMAGE_FILE_PATH.makeFilePath(filename))
                        .build();

        // 첨부파일을 트래킹하여 엔티티를 메인 메모리에 홀딩
        attachmentTracker.addTrackingAttachment(attachment, groupId);

        // 첨부파일(multipartFile)을 디렉토리에 저장
        createImageFileData(multipartFile, attachment);

        return UploadAttachmentResponseDto.builder()
                .filepath(attachment.getPath())
                .groupId(groupId)
                .build();
    }

    // TODO: 그룹 아이디를 통해 첨부파일을 fetch하고, 첨부파일을 일괄 저장. 기부 신청 폼을 저장할 때 필요함
    public void saveImageAttachmentByGroupId(long formId, String groupId) {
        // 트래킹 그룹을 group id로 찾기
        TrackingAttachmentGroup group = attachmentTracker.findGroupById(groupId);

        // 그룹에 있는 TrackingAttachment를 엔티티로 변환하여 저장
        group.getTrackingAttachmentList().forEach(tracked -> {
            // 트래킹 중인 첨부파일 불러오기
            Attachment attachment = tracked.getAttachment();

            // TODO: 기부 신청 폼 엔티티 가져오기
            ProductDonationForm donationForm = null;
            attachment.setForm(donationForm);

            // 첨부파일 경로 데이터 베이스에 저장
            attachmentRepository.save(attachment);
        });
    }


    // TODO: 신청서 폼에 해당하는 첨부파일 하나를 Fetch
    public FetchAttachmentResponseDto fetchImageAttachmentByFormId(Long productDonationFormId) {

        // TODO: 지금은 더미 데이터로 attachment를 가져옴, 바꿔야함
        Attachment attachment = attachmentRepository.findById(2L).orElseThrow();


        return FetchAttachmentResponseDto.builder()
                        .filepath(attachment.getPath())
                        .productDonationId(productDonationFormId)
                        .data(fetchImageFileData(attachment.getPath())).build();
    }


    // 신청서 폼에 해당하는 첨부파일 모두 불러오기
    public List<FetchAttachmentResponseDto> fetchImageAttachmentAllByFormId(Long productDonationFormId) {
        List<Attachment> attachments = attachmentRepository.findAllByFormId(productDonationFormId);

        return attachments.stream().map(attachment ->
                FetchAttachmentResponseDto.builder()
                        .filepath(attachment.getPath())
                        .productDonationId(productDonationFormId)
                        .data(fetchImageFileData(attachment.getPath())).build()).toList();
    }

    public void createImageFileData(MultipartFile multipartFile, Attachment attachment) {
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

    public byte[] fetchImageFileData(String filepath) {
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

    @Scheduled(cron = "0 * * * *")
    private void clearAll() {
        // 매 정각마다 만료기간이 지난 Attachment를 트래킹에서 해제하고, 파일을 삭제
        List<Attachment> attachments =
                attachmentTracker.unTrackingExpiredAttachment();

        // Attachment를 차례대로 순회하여, 엔티티로 저장되어 있지 않다면 삭제한다.
        for (Attachment attachment : attachments) {
            if (!attachmentRepository.existsById(attachment.getId())) {
                deleteFile(attachment.getPath());
            }
        }
    }

    public void deleteFile(String filepath) {
        File file = new File(filepath);

        if (!file.delete()) {
            throw new RuntimeException("file delete -> false: " + filepath);
        }
    }
}
