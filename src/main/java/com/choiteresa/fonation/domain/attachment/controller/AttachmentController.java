package com.choiteresa.fonation.domain.attachment.controller;


import com.choiteresa.fonation.domain.attachment.model.FetchAttachmentResponseDto;
import com.choiteresa.fonation.domain.attachment.model.UploadAttachmentResponseDto;
import com.choiteresa.fonation.domain.attachment.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donation/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping(value = "/{productId}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity uploadAndSaveAttachment
            (@RequestPart("attachment") MultipartFile attachment, @PathVariable long productId){
        // 클라이언트가 홀드 중인 이미지를 서버에 업로드
        UploadAttachmentResponseDto responseDto =
                attachmentService.uploadImageAttachment(attachment,productId);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping( "/{productId}")
    public ResponseEntity fetchAttachment
            (@PathVariable long productId) throws IOException {
        FetchAttachmentResponseDto result =
                attachmentService.fetchImageAttachmentByProductId(productId);

        // Header 설정
        HttpHeaders header = new HttpHeaders();
        File file = new File(result.getFilepath());
        header.add("Content-type", Files.probeContentType(file.toPath()));

        return new ResponseEntity<>(result.getData(), header, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity fetchImageTest() throws IOException {
        // 정상적인 응답 받음
        FetchAttachmentResponseDto dto =
                attachmentService.fetchTestImage();
        HttpHeaders header = new HttpHeaders();
        File file = new File(dto.getFilepath());

        header.add("Content-type", Files.probeContentType(file.toPath())); //파일의 컨텐츠타입을 직접 구해서 header에 저장

        return new ResponseEntity<>(dto.getData(), header, HttpStatus.OK);
    }
}
