package com.choiteresa.fonation.domain.member.controller;

import com.choiteresa.fonation.domain.member.dto.*;
import com.choiteresa.fonation.domain.member.entity.Member;
import com.choiteresa.fonation.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register/user")
    @ResponseBody
    public ResponseEntity<UserRegisterResponseDto> registerUser(@RequestBody UserRegisterRequestDto memberDto){
        return ResponseEntity.ok(
                UserRegisterResponseDto.fromEntity(memberService.registerUserMember(memberDto)));
    }
    @PostMapping("/register/admin")
    @ResponseBody
    public ResponseEntity<AdminRegisterResponseDto> registerAdmin(@RequestBody AdminRegisterRequestDto memberDto){
        return ResponseEntity.ok(
                AdminRegisterResponseDto.fromEntity(memberService.registerAdminMember(memberDto)));
    }

    @PostMapping("/login/user")
    public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody UserLoginRequestDto loginRequest){
        return ResponseEntity.ok(
                UserLoginResponseDto.fromEntity(memberService.loginUser(loginRequest)));
    }
    @PostMapping("/login/admin")
    public ResponseEntity<AdminLoginResponseDto> loginAdmin(@RequestBody AdminLoginRequestDto loginRequest){
        return ResponseEntity.ok(
                AdminLoginResponseDto.fromEntity(memberService.loginAdmin(loginRequest)));
    }
    @GetMapping("/summary/{member_id}")
    public ResponseEntity<SummaryResponseDto> summaryMemerInfo(@RequestBody SummaryRequestDto summaryRequest){
        Optional<Member> findMember = memberService.summaryMemberInfo(summaryRequest);

        if(findMember.isPresent()){
            SummaryDto summary = SummaryDto.fromEntity(findMember.get());
            return ResponseEntity.ok(SummaryResponseDto.fromEntity(1,summary));
        } else{
            return ResponseEntity.ok(SummaryResponseDto.fromEntity(-1,null));
        }
    }
}
