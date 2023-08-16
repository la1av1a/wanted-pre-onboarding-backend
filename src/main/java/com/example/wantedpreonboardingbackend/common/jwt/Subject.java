package com.example.wantedpreonboardingbackend.common.jwt;

import com.example.wantedpreonboardingbackend.member.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Subject {

    private final Long id;
    private final String memberName;
    private final Role role;

    @Builder
    public Subject(Long id, String memberName,Role role) {
        this.id = id;
        this.memberName = memberName;
        this.role = role;
    }

    public static Subject atk(Long id, String memberName, Role role) {
        return Subject.builder()
            .id(id)
            .memberName(memberName)
            .role(role)
            .build();
    }
}
