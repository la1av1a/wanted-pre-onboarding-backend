package com.example.wantedpreonboardingbackend.member.presentation.dto;

import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.Role;
import jakarta.validation.constraints.Pattern;

public record MemberRequestDTO(@Pattern(regexp = "^.+@.+$") String memberName,
                               @Pattern(regexp = "^.{8,}$") String memberPw) {

    public static Member toEntity(String memberName, String encodedMemberPw, Role role) {
        return new Member(memberName, encodedMemberPw, role);
    }

}
