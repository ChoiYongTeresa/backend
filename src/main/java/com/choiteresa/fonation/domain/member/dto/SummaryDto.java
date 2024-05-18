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
public class SummaryDto {
    Long id;
    String memberName;
    String phoneNumber;
    String email;

    public static SummaryDto fromEntity(Member member){
        return SummaryDto.builder()
                .id(member.getId())
                .memberName(member.getMemberName())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .build();
    }
}
