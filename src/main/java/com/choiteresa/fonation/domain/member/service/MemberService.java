package com.choiteresa.fonation.domain.member.service;

import com.choiteresa.fonation.domain.member.dto.AdminLoginRequestDto;
import com.choiteresa.fonation.domain.member.dto.AdminRegisterRequestDto;
import com.choiteresa.fonation.domain.member.dto.UserLoginRequestDto;
import com.choiteresa.fonation.domain.member.dto.UserRegisterRequestDto;
import com.choiteresa.fonation.domain.member.entity.Member;
import com.choiteresa.fonation.domain.member.repository.MemberRepository;
import com.choiteresa.fonation.domain.role.entity.Role;
import com.choiteresa.fonation.domain.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    public int registerUserMember(UserRegisterRequestDto memberDto){

        Optional<Role> role = roleRepository.findById(1L);

        if(role.isPresent()){
            System.out.println(role.isPresent());
            Member member = memberDto.toEntity(role.get());
            if(validateMember(member.getUserId())){
                return -1;
            }
            Member newMember = memberRepository.save(member);
            System.out.print(newMember);
        }

        return 1;
    }
    public int registerAdminMember(AdminRegisterRequestDto memberDto){

        Optional<Role> role = roleRepository.findById(2L);

        if(role.isPresent()){
            System.out.println(role.isPresent());
            Member member = memberDto.toEntity(role.get());
            if(validateMember(member.getUserId())){
                return -1;
            }
            Member newMember = memberRepository.save(member);
            System.out.print(newMember);
        }

        return 1;
    }

    public boolean validateMember(String userId){
        Optional<Member> findMember = memberRepository.findByUserId(userId);
        System.out.println(findMember.isPresent());
        return findMember.isPresent();
    }

    public int loginUser(UserLoginRequestDto loginRequest){
        Optional<Member> findMember = memberRepository.findByUserId(loginRequest.getUserId());

        if (findMember.isPresent() && findMember.get().getPassword() == loginRequest.getPassword()){
            return 1;

        } else return -1;
    }

    public int loginAdmin(AdminLoginRequestDto loginRequest){
        Optional<Member> findMember = memberRepository.findByUserId(loginRequest.getUserId());

        if (findMember.isPresent() && findMember.get().getPassword().equals(loginRequest.getPassword())){
            return 1;

        } else {
            System.out.println(findMember.isPresent());
            System.out.println(findMember.get().getPassword());
            System.out.println(loginRequest.getPassword());
            return -1;
        }
    }
}
