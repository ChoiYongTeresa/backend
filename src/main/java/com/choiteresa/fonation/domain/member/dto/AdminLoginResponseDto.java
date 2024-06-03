package com.choiteresa.fonation.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminLoginResponseDto {
    private int status;
    private Long foodmarketId;

    public static AdminLoginResponseDto fromEntity(int status, Long foodmarketId) {
        return AdminLoginResponseDto.builder()
                .status(status)
                .foodmarketId(foodmarketId)
                .build();
    }
}