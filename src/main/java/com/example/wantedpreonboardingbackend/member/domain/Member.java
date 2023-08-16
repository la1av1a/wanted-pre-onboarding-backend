package com.example.wantedpreonboardingbackend.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column
    private String memberName;

    @Column
    private String memberPw;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime LastModifiedDate;

    public Member(String memberName, String memberPw) {
        this.memberName = memberName;
        this.memberPw = memberPw;
    }
}
