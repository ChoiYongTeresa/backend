package com.choiteresa.fonation.domain.foodmarket_product_donation_form.dto;

import com.choiteresa.fonation.domain.member.dto.AdminRegisterResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class MarketSelectionResponseDto {
    private int status;

    public static MarketSelectionResponseDto fromEntity(int status){
        return MarketSelectionResponseDto.builder()
                .status(status)
                .build();
    }
}
