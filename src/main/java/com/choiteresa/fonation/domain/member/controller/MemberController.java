package com.choiteresa.fonation.domain.member.controller;

import com.choiteresa.fonation.domain.member.dto.*;
import com.choiteresa.fonation.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseEntity<UserRegisterResponseDto> registerAdmin(@RequestBody AdminRegisterRequestDto memberDto){
        return ResponseEntity.ok(
                UserRegisterResponseDto.fromEntity(memberService.registerAdminMember(memberDto)));
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
}
