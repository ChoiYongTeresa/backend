package com.choiteresa.fonation.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private int status;
    private Long foodMarketId;

    private String role;

    public static LoginResponseDto fromEntity(int status, Long foodmarketId, String role) {
        return LoginResponseDto.builder()
                .status(status)
                .foodMarketId(foodmarketId)
                .role(role)
                .build();
    }
}