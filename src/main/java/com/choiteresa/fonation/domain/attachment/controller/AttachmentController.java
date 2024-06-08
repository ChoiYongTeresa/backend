package com.choiteresa.fonation.domain.attachment.controller;


import com.choiteresa.fonation.domain.attachment.model.FetchAttachmentResponseDto;
import com.choiteresa.fonation.domain.attachment.model.SaveAttachmentRequestDto;
import com.choiteresa.fonation.domain.attachment.model.UploadAttachmentRequestDto;
import com.choiteresa.fonation.domain.attachment.model.UploadAttachmentResponseDto;
import com.choiteresa.fonation.domain.attachment.service.AttachmentService;
import jakarta.servlet.http.HttpSession;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donation/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping(value = "/upload/{formId}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity uploadAndSaveAttachment
            (@RequestPart("attachment") MultipartFile attachment, @PathVariable long formId){
        // 클라이언트가 홀드 중인 이미지를 서버에 업로드
        UploadAttachmentResponseDto responseDto =
                attachmentService.uploadImageAttachmentAll(attachment,formId);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping( "/{formId}")
    public ResponseEntity fetchAttachment
            (@PathVariable long formId) throws IOException {
        List<FetchAttachmentResponseDto> result =
                attachmentService.fetchImageAttachmentAllByFormId(2L);

        HttpHeaders header = new HttpHeaders();
        File file = new File(result.get(0).getFilepath());

        header.add("Content-type", Files.probeContentType(file.toPath())); //파일의 컨텐츠타입을 직접 구해서 header에 저장

        return new ResponseEntity<>(result.stream().
                map(FetchAttachmentResponseDto::getData).toList(), header, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity fetchImageTest() throws IOException {
        // 정상적인 응답 받음
        FetchAttachmentResponseDto dto =
                attachmentService.fetchImageAttachmentByFormId(2L);
        HttpHeaders header = new HttpHeaders();
        File file = new File(dto.getFilepath());

        header.add("Content-type", Files.probeContentType(file.toPath())); //파일의 컨텐츠타입을 직접 구해서 header에 저장

        return new ResponseEntity<>(dto.getData(),header, HttpStatus.OK);
    }


    @PostMapping("/old/upload")
    public ResponseEntity uploadAttachmentOnMemory
            (@RequestParam MultipartFile attachment, HttpSession attachmentSession){
        // 이미지 하나를 서버에 업로드 (메모리에 첨부파일을 등록)
        UploadAttachmentResponseDto responseDto =
                attachmentService.uploadImageAttachment(attachment, attachmentSession.getId());
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/old/save")
    public ResponseEntity saveAttachmentOnDataBase
            (@RequestBody SaveAttachmentRequestDto dto){
        // 이미지 하나를 서버에 업로드 (데이터베이스에 첨부파일을 등록)
        attachmentService.saveImageAttachmentByGroupId(dto.getProductionFormId(), dto.getGroupId());
        return ResponseEntity.ok(null);
    }
}
