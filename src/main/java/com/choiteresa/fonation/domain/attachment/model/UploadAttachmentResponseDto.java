package com.choiteresa.fonation.domain.attachment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UploadAttachmentResponseDto {
    String filepath;
}
