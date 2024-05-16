package com.choiteresa.fonation.domain.member.dto;

import com.choiteresa.fonation.domain.member.entity.Member;
import com.choiteresa.fonation.domain.role.entity.Role;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AdminRegisterRequestDto {
    private String userId;
    private String password;
    private String email;
    private String userName;
    private String phoneNumber;
    private String address;

    public Member toEntity(Role role){
        return Member.builder()
                .userId(this.userId)
                .password(this.password)
                .email(this.email)
                .userName(this.userName)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .role(role)
                .build();
    }

}
