package com.choiteresa.fonation.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRegisterResponseDto {
    private int status;

    public static UserRegisterResponseDto fromEntity(int status){
        return UserRegisterResponseDto.builder()
                .status(status)
                .build();
    }
}
