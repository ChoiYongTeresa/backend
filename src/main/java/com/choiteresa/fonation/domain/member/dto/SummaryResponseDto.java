package com.choiteresa.fonation.domain.member.dto;

import com.choiteresa.fonation.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponseDto {
    int status;
    SummaryDto summary;

    public static SummaryResponseDto fromEntity(int status, SummaryDto summary){
        return SummaryResponseDto.builder()
                .status(status)
                .summary(summary)
                .build();
    }
}
