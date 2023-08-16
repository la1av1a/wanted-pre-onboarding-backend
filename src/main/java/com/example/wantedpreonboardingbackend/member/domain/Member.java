package com.example.wantedpreonboardingbackend.member.domain;

import com.example.wantedpreonboardingbackend.common.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column
    private String memberName;

    @Column
    private String memberPw;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String memberName, String memberPw, Role role) {
        this.memberName = memberName;
        this.memberPw = memberPw;
        this.role = role;
    }
}
