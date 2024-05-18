package com.choiteresa.fonation.domain.member.dto;

import com.choiteresa.fonation.domain.member.entity.Member;
import com.choiteresa.fonation.domain.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserRegisterRequestDto {
    private String memberId;
    private String password;
    private String email;
    private String memberName;
    private String phoneNumber;
    private String address;

    public Member toEntity(Role role){
        return Member.builder()
                .memberId(this.memberId)
                .password(this.password)
                .email(this.email)
                .memberName(this.memberName)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .role(role)
                .build();
    }

}
