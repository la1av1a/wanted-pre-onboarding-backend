package com.example.wantedpreonboardingbackend.member.presentation;

import com.example.wantedpreonboardingbackend.common.response.ResponseDTO;
import com.example.wantedpreonboardingbackend.member.application.MemberService;
import com.example.wantedpreonboardingbackend.member.presentation.dto.MemberSignUpRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity<ResponseDTO<Void>> MemberSignUp(@RequestBody MemberSignUpRequestDTO requestDTO){
        memberService.memberSignUp(requestDTO);

        return new ResponseEntity<>(ResponseDTO.ofSuccess("회원가입에 성공하였습니다."), HttpStatus.OK);
    }
}
