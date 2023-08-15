package com.example.wantedpreonboardingbackend.member.presentation;

import com.example.wantedpreonboardingbackend.common.response.ResponseDTO;
import com.example.wantedpreonboardingbackend.member.application.MemberService;
import com.example.wantedpreonboardingbackend.member.presentation.dto.MemberRequestDTO;
import com.example.wantedpreonboardingbackend.member.presentation.dto.MemberTokenResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<ResponseDTO<Void>> MemberSignUp(@RequestBody @Valid MemberRequestDTO requestDTO){
        memberService.memberSignUp(requestDTO);

        return new ResponseEntity<>(ResponseDTO.ofSuccess("회원가입에 성공하였습니다."), HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<ResponseDTO<MemberTokenResponseDTO>> signIn(@RequestBody @Valid MemberRequestDTO requestDto) {

        return new ResponseEntity<>(new ResponseDTO<>(memberService.memberSignIn(requestDto), "로그인에 성공하였습니다"), HttpStatus.OK);
    }


}
