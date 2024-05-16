package com.choiteresa.fonation.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminLoginResponseDto {
    private int status;

    public static AdminLoginResponseDto fromEntity(int status) {
        return AdminLoginResponseDto.builder()
                .status(status)
                .build();
    }
}