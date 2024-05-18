package com.choiteresa.fonation.domain.member.service;

import com.choiteresa.fonation.domain.member.dto.*;
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
            if(validateMember(member.getMemberId())){
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
            if(validateMember(member.getMemberId())){
                return -1;
            }
            Member newMember = memberRepository.save(member);
            System.out.print(newMember);
        }

        return 1;
    }

    public boolean validateMember(String userId){
        Optional<Member> findMember = memberRepository.findByMemberId(userId);
        System.out.println(findMember.isPresent());
        return findMember.isPresent();
    }

    public int loginUser(UserLoginRequestDto loginRequest){
        Optional<Member> findMember = memberRepository.findByMemberId(loginRequest.getMemberId());

        if (findMember.isPresent() &&
                findMember.get().getPassword().equals(loginRequest.getPassword()) &&
                findMember.get().getRole().getRoleName().equals("user") ){
            return 1;

        } else return -1;
    }

    public int loginAdmin(AdminLoginRequestDto loginRequest){
        Optional<Member> findMember = memberRepository.findByMemberId(loginRequest.getMemberId());

        if (findMember.isPresent() &&
                findMember.get().getPassword().equals(loginRequest.getPassword()) &&
                findMember.get().getRole().getRoleName().equals("admin")){
            return 1;

        } else {
            return -1;
        }
    }
    public Optional<Member> summaryMemberInfo(SummaryRequestDto summaryRequest){
        Optional<Member> findMember = memberRepository.findByMemberId(summaryRequest.getMemberId());

        return findMember;
    }
}
