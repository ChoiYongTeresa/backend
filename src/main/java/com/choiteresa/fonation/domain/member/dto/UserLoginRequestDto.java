package com.choiteresa.fonation.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserLoginRequestDto {
    private String memberId;
    private String password;

//    public Member toEntity(Role role){
//        return Member.builder()
//                .userId(this.userId)
//                .password(this.password)
//                .email(this.email)
//                .userName(this.userName)
//                .phone_number(this.phone_number)
//                .address(this.address)
//                .role(role)
//                .build();
//    }

}