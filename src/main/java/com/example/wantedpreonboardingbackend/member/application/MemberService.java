package com.example.wantedpreonboardingbackend.member.application;

import com.example.wantedpreonboardingbackend.common.security.exception.MemberAlreadyExistsException;
import com.example.wantedpreonboardingbackend.member.presentation.dto.MemberSignUpRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberCRUDService memberCRUDService;
    private final PasswordEncoder passwordEncoder;

    public void memberSignUp(MemberSignUpRequestDTO requestDTO){
        if(memberCRUDService.isMemberExist(requestDTO.memberName())){
            throw new MemberAlreadyExistsException("이미 존재하는 멤버입니다.");
        }

        memberCRUDService.saveMember(MemberSignUpRequestDTO.toEntity(
            requestDTO.memberName(),encodePassword(requestDTO.memberPw())));
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
}
