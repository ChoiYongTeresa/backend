package com.choiteresa.fonation.domain.member.dto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDto {
    private int status;

    public static UserLoginResponseDto fromEntity(int status){
        return UserLoginResponseDto.builder()
                .status(status)
                .build();
    }
}