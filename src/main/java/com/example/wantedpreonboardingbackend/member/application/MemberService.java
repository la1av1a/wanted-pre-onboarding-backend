package com.example.wantedpreonboardingbackend.member.application;

import com.example.wantedpreonboardingbackend.common.jwt.JwtUtil;
import com.example.wantedpreonboardingbackend.common.jwt.Subject;
import com.example.wantedpreonboardingbackend.common.exception.MemberAlreadyExistsException;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.Role;
import com.example.wantedpreonboardingbackend.member.presentation.dto.MemberRequestDTO;
import com.example.wantedpreonboardingbackend.member.presentation.dto.MemberTokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberCRUDService memberCRUDService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 회원가입
     * @param requestDTO
     */
    public void memberSignUp(MemberRequestDTO requestDTO){
        if(memberCRUDService.isMemberExist(requestDTO.memberName())){
            throw new MemberAlreadyExistsException("이미 존재하는 멤버입니다.");
        }

        memberCRUDService.saveMember(MemberRequestDTO.toEntity(
            requestDTO.memberName(),encodePassword(requestDTO.memberPw()), Role.ROLE_USER));
    }

    public MemberTokenResponseDTO memberSignIn(MemberRequestDTO requestDTO){
        Member member = memberCRUDService.findMemberByMemberName(requestDTO.memberName());

        if(!isPasswordMatch(requestDTO.memberPw(),member.getMemberPw())){
            throw new MemberAlreadyExistsException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(Subject.atk(member.getMemberId(), member.getMemberName(), member.getRole()));

        return new MemberTokenResponseDTO(token);
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    private boolean isPasswordMatch(String password, String encodedPassword){
        return passwordEncoder.matches(password,encodedPassword);
    }

}
