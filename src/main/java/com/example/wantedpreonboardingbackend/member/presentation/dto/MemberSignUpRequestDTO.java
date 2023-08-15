package com.example.wantedpreonboardingbackend.member.presentation.dto;

import com.example.wantedpreonboardingbackend.member.domain.Member;
import jakarta.validation.constraints.Pattern;

public record MemberSignUpRequestDTO(@Pattern(regexp = "^.+@.+$") String memberName,
                                     @Pattern(regexp = "^.{8,}$") String memberPw) {

    public static Member toEntity(String memberName, String encodedMemberPw) {
        return new Member(memberName, encodedMemberPw);
    }

}
