package com.choiteresa.fonation.domain.member.service;

import com.choiteresa.fonation.domain.foodmarket.entity.FoodMarket;
import com.choiteresa.fonation.domain.foodmarket.repository.FoodMarketRepository;
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
    private final FoodMarketRepository foodMarketRepository;

    public UserRegisterResponseDto registerUserMember(UserRegisterRequestDto memberDto) {

        Optional<Role> role = roleRepository.findById(1L);

        if (role.isPresent()) {
            System.out.println(role.isPresent());
            Member member = memberDto.toEntity(role.get());
            if (validateMember(member.getMemberId())) {
                return UserRegisterResponseDto.fromEntity(-1);
            }
            Member newMember = memberRepository.save(member);
            System.out.print(newMember);
        }

        return UserRegisterResponseDto.fromEntity(1);
    }

    public AdminRegisterResponseDto registerAdminMember(AdminRegisterRequestDto memberDto) {

        Optional<Role> role = roleRepository.findById(2L);

        if (role.isPresent()) {
            System.out.println(role.isPresent());
            Member member = memberDto.toEntity(role.get());
            if (validateMember(member.getMemberId())) {
                return AdminRegisterResponseDto.fromEntity(-1);
            }
            Member newMember = memberRepository.save(member);
            System.out.print(newMember);
        }

        return AdminRegisterResponseDto.fromEntity(1);
    }

    public boolean validateMember(String userId) {
        Optional<Member> findMember = memberRepository.findByMemberId(userId);
        System.out.println(findMember.isPresent());
        return findMember.isPresent();
    }

    public UserLoginResponseDto loginUser(UserLoginRequestDto loginRequest) {
        Optional<Member> findMember = memberRepository.findByMemberId(loginRequest.getMemberId());

        if (findMember.isPresent() &&
                findMember.get().getPassword().equals(loginRequest.getPassword()) &&
                findMember.get().getRole().getRoleName().equals("user")) {
            return UserLoginResponseDto.fromEntity(1);

        } else return UserLoginResponseDto.fromEntity(-1);

    }

    public AdminLoginResponseDto loginAdmin(AdminLoginRequestDto loginRequest) {
        Optional<Member> findMember = memberRepository.findByMemberId(loginRequest.getMemberId());

        if (findMember.isPresent() &&
                findMember.get().getPassword().equals(loginRequest.getPassword()) &&
                findMember.get().getRole().getRoleName().equals("admin")) {

            Optional<FoodMarket> market = foodMarketRepository.findByAdmin(findMember.get());

            if(market.isPresent()) {
                return AdminLoginResponseDto.fromEntity(1,market.get().getId());
            } else {
                return AdminLoginResponseDto.fromEntity(1,-1L);
            }

        } else {
            return AdminLoginResponseDto.fromEntity(-1,-1L);
        }
    }

    public Optional<Member> summaryMemberInfo(SummaryRequestDto summaryRequest) {
        Optional<Member> findMember = memberRepository.findByMemberId(summaryRequest.getMemberId());

        return findMember;
    }
}
