package com.choiteresa.fonation.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminRegisterResponseDto {
    private int status;

    public static AdminRegisterResponseDto fromEntity(int status){
        return AdminRegisterResponseDto.builder()
                .status(status)
                .build();
    }
}
